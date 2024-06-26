package com.project.itoon.cartoonPage

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.project.itoon.Config
import com.project.itoon.EpisodeNav.EpisodeBottomBar
import com.project.itoon.EpisodeNav.EpisodeTopBar
import com.project.itoon.EpisodeNav.NavGraphEpisode
import com.project.itoon.NavBottomBar.MainScreen
import com.project.itoon.R
import com.project.itoon.firstpageapi.CartoonAPI
import com.project.itoon.ui.theme.ITOONTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EpisodeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var context = LocalContext.current.applicationContext
            val episodeId = intent.getIntExtra("EPISODE_ID", 0)
            val cartoonid=intent.getIntExtra("CARTOON_ID",0)
            var epname = intent.getStringExtra("EPISODE_NAME")
            val epnumber = intent.getIntExtra("EPISODE_NUMBER",0)
            if(epname == null){
                epname = ""
            }
            ITOONTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    EpisodeScaffoldLayout(episodeId,cartoonid, epname,epnumber)
                }
            }

        }
    }
}


@Composable
private fun CallApi(epId:Int): SnapshotStateList<imgEp> {
    val createClient = CartoonAPI.create()
    val pageImgList = remember { mutableStateListOf<imgEp>() }
    val contextForToast = LocalContext.current.applicationContext
//    Set data here and path id ep here
    LaunchedEffect(true){
        createClient.getImgCartoon(epId)
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

@SuppressLint("RememberReturnType")
@Composable
fun CartoonThisChapter(navHostController: NavHostController,epId: Int){
    val pageImgList = CallApi(epId)
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        itemsIndexed(items = pageImgList) { index, item ->
            val path: String = "${Config().APIBaseUrl}/${item.pathUrl}"

            val listener = object : ImageRequest.Listener {
                override fun onError(request: ImageRequest, result: ErrorResult) {
                    super.onError(request, result)
                }

                override fun onSuccess(request: ImageRequest, result: SuccessResult) {
                    super.onSuccess(request, result)
                }
            }
            val imageRequest = ImageRequest.Builder(context)
                .data(path)
                .listener(listener)
                .memoryCacheKey(path)
                .diskCacheKey(path)
                .placeholder(R.drawable.loading)
                .fallback(R.drawable.loading)
                .diskCachePolicy(CachePolicy.ENABLED)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .build()

            AsyncImage(
                model = imageRequest,
                contentDescription = item.page.toString(),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillWidth,
            )
        }
    }
}
@Composable
fun EpisodeScaffoldLayout(epId:Int,cartoonid:Int,epname:String,epnum:Int){
    val navController = rememberNavController()
    val Episode = epId
    val cartoonid = cartoonid
    val epname = epname
    val epnum = epnum

    Scaffold(
        topBar = { EpisodeTopBar(epname,epnum)},
        bottomBar = { EpisodeBottomBar(navController) },
        floatingActionButtonPosition = FabPosition.End,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            NavGraphEpisode(navController = navController,Episode,cartoonid)
        }
    }
}


