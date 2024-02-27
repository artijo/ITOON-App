package com.project.itoon.TopLazyRow

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.project.itoon.Config
import com.project.itoon.NavBottomBar.BottomBar
import com.project.itoon.Setting.SharedPreferencesManager
import com.project.itoon.ShowTextTest
import com.project.itoon.cartoonPage.CartoonPage
import com.project.itoon.favoritebutton.ItemFav
import com.project.itoon.favoritebutton.ShowFavClass
import com.project.itoon.firstpageapi.Cartoon
import com.project.itoon.firstpageapi.CartoonAPI
import okhttp3.HttpUrl.Companion.toHttpUrl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun Like(navController:NavHostController){
    val creatClient = CartoonAPI.create()
    val cartoonList  = remember {
        mutableStateListOf<Cartoon>()
    }
    val favlist = remember{
        mutableStateListOf<ShowFavClass>()
    }
    val itemList = remember {
        mutableStateListOf<ItemFav>()
    }
    val contextForToast = LocalContext.current.applicationContext
    lateinit var sharedPreferenceManager : SharedPreferencesManager
    sharedPreferenceManager = SharedPreferencesManager(context = contextForToast)
    val userId by remember{ mutableStateOf(sharedPreferenceManager.userId) }


    LaunchedEffect(true) {
        cartoonList.clear()
        favlist.clear()
        itemList.clear()
        creatClient.showallfav(userId)
            .enqueue(object : Callback<List<ShowFavClass>> {
                override fun onResponse(
                    call: Call<List<ShowFavClass>>,
                    response: Response<List<ShowFavClass>>
                ) {
                    Log.i("datafav", response.body().toString())
                    response.body()?.forEach {
                        itemList.add(
                            ItemFav(
                                Cartoon(
                                    it.cartoonId,
                                    it.cartoon.name,
                                    it.cartoon.description,
                                    it.cartoon.releaseDate,
                                    it.cartoon.thumbnail,
                                    it.cartoon.totalEpisodes,
                                    it.cartoon.creatorId,
                                    it.cartoon.genreId,
                                    it.cartoon.genres,
                                    it.cartoon.creator,
                                    it.cartoon.paid,
                                    it.cartoon.price
                                ),
                                ShowFavClass(
                                    it.id,
                                    it.userId,
                                    it.cartoonId,
                                    it.favoriteDate,
                                    it.cartoon,
                                    it.user
                                )
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<List<ShowFavClass>>, t: Throwable) {
                    Toast.makeText(contextForToast, t.message, Toast.LENGTH_LONG).show()
                    Log.i("datafav", "error")
                }
            })
    }
    var isOpen by remember { mutableStateOf(false) }
    val idTextCartoon = remember { mutableStateOf("") }
    if(isOpen){
        ShowTextTest(textFor = idTextCartoon.value)
        isOpen = false
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 65.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "รายการที่ถูกใจ")
        Spacer(modifier = Modifier.padding(10.dp))
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 15.dp),
        ){
            var clickCartoon:Cartoon

            items(itemList) { item ->
                val urltext = item.cartoonlist.thumbnail
                Row(
                    modifier = Modifier
                        .width(360.dp)
                        .padding(bottom = 10.dp)
                        .clickable(
                            onClick = {
                                clickCartoon = item.cartoonlist
                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                    "data",
                                    clickCartoon
                                )
                                navController.navigate(CartoonPage.CartoonEP.route)
                            }
                        ),
                ) {
                    Box(modifier = Modifier
                        .width(90.dp)
                        .height(80.dp)


                    ){
                        if (urltext.startsWith("uploads")){
                            val replace = urltext.replace("\\","/")
                            item.cartoonlist.thumbnail = "${Config().APIBaseUrl}/"+"$replace"
                            val pathUrl = item.cartoonlist.thumbnail.toHttpUrl()
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
                            println(item.cartoonlist.thumbnail)
                            Image(
                                painter = rememberAsyncImagePainter(item.cartoonlist.thumbnail) ,
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(5.dp))
                                    .fillMaxSize()

                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(30.dp))
                    Column (Modifier.width(150.dp), verticalArrangement = Arrangement.Center){
                        Text(
                            text = item.cartoonlist.name,
                            fontSize = 15.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Medium,
                            maxLines = 2,
                            style = LocalTextStyle.current.copy(lineHeight = 15.sp),
                            overflow = TextOverflow.Ellipsis,


                            )
                        Text(
                            text= item.cartoonlist.genres.name,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray,

                            )
                    }

                }
            }
        }

    }
}