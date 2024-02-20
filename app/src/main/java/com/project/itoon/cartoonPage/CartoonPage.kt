package com.project.itoon.cartoonPage

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.project.itoon.LoginAndSignUp.User
import com.project.itoon.Setting.SharedPreferencesManager
import com.project.itoon.firstpageapi.Cartoon
import com.project.itoon.firstpageapi.CartoonAPI
import com.project.itoon.firstpageapi.Creator
import com.project.itoon.firstpageapi.Genres
import com.project.itoon.firstpageapi.edithistory
import com.project.itoon.ui.theme.ITOONTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

sealed class CartoonPage(val route:String,val name:String){
    data object CartoonEP: CartoonPage("CartoonEP_Page","หน้าเลือกตอน")
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartoonThumbnail(navController:NavHostController){
    val data = navController.previousBackStackEntry?.savedStateHandle?.get<Cartoon>("data")?:
    Cartoon(0,"","","","",0,
        0,0, Genres(0,"","","",""),
        Creator(0,0,"","","", User(0,"","","","",0))
    )

    var name by remember{ mutableStateOf(data.name) }
    var texturl by remember{ mutableStateOf(data.thumbnail) }
    var username = remember{ mutableStateOf("") }

    var cartoonurl by remember { mutableStateOf(
            if (texturl.isNotEmpty()) {
                Uri.parse(texturl)
            } else {
                null
            }
        )
    }


    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Box(modifier = Modifier.wrapContentSize()){
                Box(modifier = Modifier.fillMaxSize()){
                    Card(
                        Modifier
                            .wrapContentSize()
                            .height(350.dp),
                        shape = RoundedCornerShape(0.dp),
                        elevation = CardDefaults.cardElevation(0.dp),
                    ) {

                        Box(modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black)
                            .alpha(0.6f)

                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(cartoonurl),
                                contentDescription = "Image $texturl",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxSize()
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .alpha(0.6f)
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(
                                                Color.Transparent,
                                                Color.Black
                                            ),
                                            startY = 450f
                                        )
                                    )
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .padding(start = 20.dp,top = 75.dp)
                    ){
                        Box(modifier =
                        Modifier
                            .size(250.dp)
                        ) {
                            Column (
                                Modifier.fillMaxSize()
                            ){
                                Text(
                                    text = "$name",
                                    fontSize = 25.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold,
                                    lineHeight = 30.sp
                                )
                                Text(
                                    text = "${data.genres.name}",
                                    fontSize = 20.sp,
                                    color = Color.White,
                                    lineHeight = 20.sp
                                )
                                Text(
                                    text = "${data.creator?.user?.name}",
                                    fontSize = 20.sp,
                                    color = Color.White,
                                    lineHeight = 20.sp
                                )
                                Spacer(modifier = Modifier.padding(20.dp))
                                Text(
                                    text="${data.description}",
                                    fontSize = 17.sp,
                                    color = Color.White,
                                    lineHeight = 18.sp
                                )
                            }
                        }
                    }
            }
        }
        CartoonAllEp(navController)
    }

}




@Composable
private fun ItemLayOutColumn(
    episode: CartoonAllEp,
    context: Context = LocalContext.current.applicationContext
){
    val navController = rememberNavController()
    val thumbnailtext by remember{ mutableStateOf(episode.thumbnail) }
    val contextForToast = LocalContext.current.applicationContext
    lateinit var sharedPreferenceManager : SharedPreferencesManager
    sharedPreferenceManager = SharedPreferencesManager(context = contextForToast)
    val userId by remember{ mutableStateOf(sharedPreferenceManager.userId) }
    var episodeurl by remember { mutableStateOf(
        if (thumbnailtext.isNotEmpty()) {
            Uri.parse("http://10.0.2.2:3000/"+"${thumbnailtext}")
        } else {
            null
        }
    )
    }

    Box(
        Modifier.fillMaxWidth()
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    updatehistoryfuntion(userId,episode.cartoonId,episode.episodeNumber)
                    startEpisodeActivity(
                        context, episode.id, episode.cartoonId, episode.name, episode.episodeNumber
                    )
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(episodeurl),
                contentDescription = episode.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "Ep.${episode.episodeNumber}"+"- ${episode.name}"
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "#${episode.episodeNumber}"

                )
            }

        }

    }
    Divider(color = Color.LightGray, thickness = 1.dp)
}



@Composable
fun CartoonAllEp(navController:NavHostController){
    val createClient = CartoonAPI.create()
    val allEpisode = remember { mutableStateListOf<CartoonAllEp>() }
    val contextForToast = LocalContext.current.applicationContext
    val data = navController.previousBackStackEntry?.savedStateHandle?.get<Cartoon>("data")?:
    Cartoon(0,"","","","",0,
        0,0, Genres(0,"","","","")
            , Creator(0,0,"","","" ,User(0,"","","","",0))
    )

    LaunchedEffect(true){
        createClient.getAEC(data.id)
            .enqueue(object: Callback<List<CartoonAllEp>>{
                override fun onResponse(call: Call<List<CartoonAllEp>>,
                                        response: Response<List<CartoonAllEp>>
                ){
                    response.body()?.forEach{
                       allEpisode.add(
                           CartoonAllEp(
                               it.id,
                               it.name,
                               it.episodeNumber,
                               it.releaseDate,
                               it.thumbnail,
                               it.cartoonId
                           )
                       )
                    }
                }
                override fun onFailure(call: Call<List<CartoonAllEp>>, t: Throwable) {
                    Toast.makeText(contextForToast,"Error on Failure" + t.message, Toast.LENGTH_LONG).show()
                }
        })
    }
    Column(
        Modifier.fillMaxWidth()
    ) {
        allEpisode.forEach{item->
            ItemLayOutColumn(episode = item)
        }
    }
}




@Preview (showBackground = true)
@Composable
fun Example(){
    ITOONTheme {

    }
}

@Composable
fun SelectPage(navHostController:NavHostController){
//    var navHostController = rememberNavController()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CartoonThumbnail(navController = navHostController)
    }
}

fun startEpisodeActivity(c: Context,episodeId:Int,cartoonid:Int,epname:String,epnum:Int){
    val intent = Intent(c, EpisodeActivity::class.java).apply {
        putExtra("EPISODE_ID",episodeId)
        putExtra("CARTOON_ID",cartoonid)
        putExtra("EPISODE_NAME",epname)
        putExtra("EPISODE_NUMBER",epnum)
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
    c.startActivity(intent)

}


fun updatehistoryfuntion(uid:Int,cid:Int,epnum:Int){
    val creatClient = CartoonAPI.create()
    creatClient.updatehistory(uid.toString(),cid.toString(),epnum.toString())
        .enqueue(object : Callback<edithistory>{
            override fun onResponse(call: Call<edithistory>, response: Response<edithistory>) {
                Log.i("check","onrespond")
            }

            override fun onFailure(call: Call<edithistory>, t: Throwable) {
                Log.i("check","onfail")
            }
        }

        )
}
