package com.project.itoon.TopLazyRow

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.project.itoon.API
import com.project.itoon.Commentpage.Episode
import com.project.itoon.Commentpage.commentdata
import com.project.itoon.Commentpage.getBottomLineShape
import com.project.itoon.Config
import com.project.itoon.LoginAndSignUp.User
import com.project.itoon.Setting.SharedPreferencesManager
import com.project.itoon.firstpageapi.Cartoon
import com.project.itoon.firstpageapi.Genres
import okhttp3.HttpUrl.Companion.toHttpUrl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun Comment(navHostController: NavHostController){
    val contextForToast = LocalContext.current.applicationContext
    lateinit var sharedPreferences: SharedPreferencesManager
    sharedPreferences = SharedPreferencesManager(contextForToast)
    val userId by remember { mutableStateOf(sharedPreferences.userId) }
    val initialcomment = commentdata("",0,User(0,"","","","",0),0,
        Episode(0,"",0,"","",
        0, Cartoon(0,"","","","",0,0,0,
                Genres(0,"","","",""),
            com.project.itoon.firstpageapi.Creator(0,0,"","","",
                User(0,"","","","",0)
            )
            ,false,0)

        ),
    )
    var commentList = remember{ mutableStateListOf<commentdata>() }
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()
    val createClient = API.create()
    LaunchedEffect(lifecycleState) {
        when (lifecycleState) {
            Lifecycle.State.DESTROYED -> {}
            Lifecycle.State.INITIALIZED -> {}
            Lifecycle.State.CREATED -> {}
            Lifecycle.State.STARTED -> {}
            Lifecycle.State.RESUMED -> {
                showUserComment(commentList,contextForToast,userId)
            }
        }
    }
    Column {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ){
            itemsIndexed(items = commentList,
            ){index,item->
                Card(
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                        .fillMaxWidth()
                        .border(
                            width = 2.dp,
                            color = Color.Gray,
                            shape = getBottomLineShape(2.dp)
                        ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White)
                ){
                    Row (
                        Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                    ){
                        Text(
                            text ="Name : ${item.user.name}\n"
                                    +"Cartoon Name : ${item.episode.cartoon.name}\n"
                                    +"Episode : ${item.episode.epNumber}\n"
                                    +"Comment : ${item.content}",
                            Modifier.weight(0.85f)
                                .padding(10.dp)
                        )
                    }
                }
            }
        }
    }
}
fun showUserComment(commentList:MutableList<commentdata>, context: Context,uid:Int){
    val createClient = API.create()
    createClient.getUserComment(uid)
        .enqueue(object : Callback<List<commentdata>> {
            override fun onResponse(call: Call<List<commentdata>>,
                                    response: Response<List<commentdata>>
            ) {
                commentList.clear()
                response.body()?.forEach {
                    commentList.add(commentdata(it.content,it.userId,it.user,it.episodeId,it.episode))
                    Log.i("episodeID",it.episode.toString())
                }
            }
            override fun onFailure(call: Call<List<commentdata>>, t: Throwable) {
                Toast.makeText(context,"ShowFailonFailure"+t.message,
                    Toast.LENGTH_LONG).show()
            }
        })
}
