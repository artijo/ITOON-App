package com.project.itoon.TopLazyRow

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.project.itoon.LoginAndSignUp.LoginAndSignUp
import com.project.itoon.LoginAndSignUp.startLoginActivity
import com.project.itoon.NavBottomBar.BottomBar
import com.project.itoon.Setting.SharedPreferencesManager
import com.project.itoon.ShowTextTest
import com.project.itoon.cartoonPage.CartoonPage
import com.project.itoon.cartoonPage.startEpisodeActivity
import com.project.itoon.firstpageapi.Cartoon
import com.project.itoon.firstpageapi.CartoonAPI
import com.project.itoon.ui.theme.ITOONTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@SuppressLint("RestrictedApi")
@Composable
fun Recently(navHostController: NavHostController){
    val createClient = CartoonAPI.create()
    val cartoonList  = remember {
        mutableStateListOf<Cartoon>()
    }
    val eplist = remember{
        mutableStateListOf<allhistory>()
    }
    var count = remember {
        0
    }
    val itemList = remember {
        mutableStateListOf<Item>()
    }
    val contextForToast = LocalContext.current.applicationContext
    lateinit var sharedPreferenceManager : SharedPreferencesManager
    sharedPreferenceManager = SharedPreferencesManager(context = contextForToast)
    val userId by remember{ mutableStateOf(sharedPreferenceManager.userId) }
    cartoonList.clear()
    eplist.clear()
    createClient.allhistory(userId.toString())
        .enqueue(object :Callback<List<allhistory>>{
            override fun onResponse(call: Call<List<allhistory>>, response: Response<List<allhistory>>) {
                Log.i("data",response.body().toString())
                Log.i("data","response.body().toString()")

                response.body()?.forEach {
                    itemList.add(
                        Item(Cartoon(it.cartoonId,it.cartoon.name,it.cartoon.description,it.cartoon.releaseDate,it.cartoon.thumbnail,it.cartoon.totalEpisodes,it.cartoon.creatorId,it.cartoon.genreId,it.cartoon.genres,it.cartoon.creator),
                            allhistory(it.id,it.userId,it.cartoonId,it.episodeId,it.cartoon,it.user,it.episode))
                    )
                }
            }

            override fun onFailure(call: Call<List<allhistory>>, t: Throwable) {
                Toast.makeText(contextForToast,"Error on Failure"+t.message,Toast.LENGTH_LONG).show()
                Log.i("data","error")
            }
        })

    var isOpen by remember { mutableStateOf(false) }
    val idTextCartoon = remember { mutableStateOf("") }
    if(isOpen){
        ShowTextTest(textFor = idTextCartoon.value)
        isOpen = false
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "RecentlyPage")
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
                                    startEpisodeActivity(
                                        contextForToast,
                                        item.eplist.episodeId,
                                        item.eplist.id,
                                        item.eplist.episode.episodeName,
                                        item.eplist.episode.episodeNumber
                                    )
                                }
                            ),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Row(verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(item.cartoonlist.thumbnail),
                                contentDescription = item.cartoonlist.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .width(45.dp)
                                    .height(45.dp)
                                    .clip(RoundedCornerShape(10.dp))
                            )
                            Spacer(modifier = Modifier.width(width = 10.dp))
                            Text(
                                text = item.cartoonlist.name,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                lineHeight = 3.sp,
                            )

                        }
                        Spacer(modifier = Modifier.width(30.dp))
                        Column(Modifier.align(Alignment.CenterVertically)
                        ) {
                            Text(
                                text = "#"+item.eplist.episode.episodeNumber.toString(),
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                lineHeight = 3.sp,
                            )

                        }
                    }
                }
        }

    }
}

