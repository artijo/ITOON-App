package com.project.itoon.cartoonPage

import android.graphics.Color
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.project.itoon.EpisodeNav.EpisodeBottomBar
import com.project.itoon.EpisodeNav.EpisodeTopBar
import com.project.itoon.EpisodeNav.NavGraphEpisode

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.project.itoon.firstpageapi.CartoonAPI
import okhttp3.HttpUrl.Companion.toHttpUrl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
private fun CallApi(): SnapshotStateList<imgEp> {
    val createClient = CartoonAPI.create()
    val pageImgList = remember { mutableStateListOf<imgEp>() }
    val contextForToast = LocalContext.current.applicationContext
//    Set data here and path id ep here
    LaunchedEffect(true){
        createClient.getImgCartoon(3)
            .enqueue(object: Callback<List<imgEp>>{
                override fun onResponse(call: Call<List<imgEp>>, response: Response<List<imgEp>>) {
                    response.body()?.forEach{
                        pageImgList.add(
                            imgEp(
                                it.id,
                                it.page,
                                it.pathUrl
                            )
                        )
                    }
                }
                override fun onFailure(call: Call<List<imgEp>>, t: Throwable) {
                    Toast.makeText(contextForToast,"Error on Failure" + t.message, Toast.LENGTH_LONG).show()
                }
            })
    }
    return pageImgList
}

@Composable
fun CartoonThisChapter(navHostController: NavHostController){
    val pageImgList = CallApi()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        itemsIndexed(items = pageImgList) { index, item ->
            val pathFromDatabase = item.pathUrl.replace("\\", "/")
            val path: String = "http://10.0.2.2:3000/$pathFromDatabase"
            val pathURL = path.toHttpUrl()
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(pathURL),
                    contentDescription = null,
                    Modifier.fillMaxSize()
                )
                Text(text = "kuy")
            }
        }
    }
}
@Composable
fun EpisodeScaffoldLayout(){
    val navController = rememberNavController()
    Scaffold(
        topBar = { EpisodeTopBar()},
        bottomBar = { EpisodeBottomBar(navController) },
        floatingActionButtonPosition = FabPosition.End,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            NavGraphEpisode(navController = navController)
        }
    }
}
