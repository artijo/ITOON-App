package com.project.itoon.TopLazyRow

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.SpaceAround
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.project.itoon.Commentpage.getBottomLineShape
import com.project.itoon.Config
import com.project.itoon.LoginAndSignUp.User
import com.project.itoon.Setting.SharedPreferencesManager
import com.project.itoon.cartoonPage.CartoonPage
import com.project.itoon.firstpageapi.Cartoon
import com.project.itoon.firstpageapi.CartoonAPI
import com.project.itoon.firstpageapi.Genres
import okhttp3.HttpUrl.Companion.toHttpUrl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun MyBuyCartoon(navController: NavHostController){
    val contextForToast = LocalContext.current.applicationContext
    lateinit var sharedPreferences: SharedPreferencesManager
    sharedPreferences = SharedPreferencesManager(contextForToast)
    val userId by remember { mutableStateOf(sharedPreferences.userId) }
    val initialbuycartoon = BoughtCartoon(0,0,0, Cartoon(0,"","","",
        "",0,0,0, Genres(0,"","","",""),
        com.project.itoon.firstpageapi.Creator(0,0,"","","",
            User(0,"","","","",0)),
        false,0))
    var boughtList = remember{ mutableStateListOf<BoughtCartoon>() }
    var cartoonList = remember{ mutableStateListOf<Cartoon>() }
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()
    LaunchedEffect(lifecycleState) {
        when (lifecycleState) {
            Lifecycle.State.DESTROYED -> {}
            Lifecycle.State.INITIALIZED -> {}
            Lifecycle.State.CREATED -> {}
            Lifecycle.State.STARTED -> {}
            Lifecycle.State.RESUMED -> {
                showBoughtCartoon(boughtList,contextForToast,userId)
            }
        }
    }
    Column(
        Modifier.padding(top = 65.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ){
            var clickCartoon:Cartoon
            itemsIndexed(items = boughtList,
            ){index,item->
                Card(
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                        .fillMaxWidth()
                        .clickable {
                            clickCartoon = item.cartoon
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                "data",
                                clickCartoon
                            )
                            navController.navigate(CartoonPage.CartoonEP.route)
                        },
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                    ),
                ){
                    Row (
                        Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Box(modifier = Modifier
                            .width(90.dp)
                            .height(80.dp)

                        ){
                            val urltext = item.cartoon.thumbnail
                            if (urltext.startsWith("uploads")){
                                val replace = urltext.replace("\\","/")
                                item.cartoon.thumbnail = "${Config().APIBaseUrl}/"+"$replace"
                                val pathUrl = item.cartoon.thumbnail.toHttpUrl()
                                println(pathUrl)
                                Image(
                                    painter = rememberAsyncImagePainter(pathUrl) ,
                                    contentDescription = "",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(5.dp))
                                        .fillMaxSize()

                                )
                            }else{
                                println(item.cartoon.thumbnail)
                                Image(
                                    painter = rememberAsyncImagePainter(item.cartoon.thumbnail) ,
                                    contentDescription = "",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(5.dp))
                                        .fillMaxSize()

                                )
                            }
                        }
                        Column (
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 15.dp)
                        ){
                            Text(
                                text = "${item.cartoon.name}",
                                fontSize = 15.sp,
                            )
                            Text(
                                text = "${item.cartoon.genres.name}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Gray,
                            )
                        }
                    }
                }
            }
        }
        if (boughtList.isEmpty()){
            Text(text = "You Want to Buy Cartoon First",
                Modifier.padding(top = 300.dp))
        }
    }
}
fun showBoughtCartoon(boughtList:MutableList<BoughtCartoon>, context: Context, uid:Int){
    val createClient = CartoonAPI.create()
    createClient.showbuyCartoon(uid)
        .enqueue(object : Callback<List<BoughtCartoon>> {
            override fun onResponse(call: Call<List<BoughtCartoon>>,
                                    response: Response<List<BoughtCartoon>>
            ) {
                boughtList.clear()
                response.body()?.forEach {
                    boughtList.add(BoughtCartoon(it.id,it.userId,it.cartoonId,it.cartoon))
                }
            }
            override fun onFailure(call: Call<List<BoughtCartoon>>, t: Throwable) {
                Toast.makeText(context,"Show Fail on Failure"+t.message,
                    Toast.LENGTH_LONG).show()
            }
        })
}