package com.project.itoon.Commentpage

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import com.project.itoon.API
import com.project.itoon.EpisodeNav.EpisodeBottom
import com.project.itoon.LoginAndSignUp.User
import com.project.itoon.R
import com.project.itoon.Setting.SharedPreferencesManager
import com.project.itoon.firstpageapi.Cartoon
import com.project.itoon.firstpageapi.Creator
import com.project.itoon.firstpageapi.Genres
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("RestrictedApi")
@Composable
fun CommentPage(navController: NavHostController,epid : Int,cartoonid: Int){
    var textFieldComment by remember{ mutableStateOf("") }
    val contextForToast = LocalContext.current.applicationContext
    lateinit var sharedPreferences: SharedPreferencesManager
    sharedPreferences = SharedPreferencesManager(contextForToast)
    val userId by remember { mutableStateOf(sharedPreferences.userId) }
    val initialcomment = commentdata("",0,0,Episode(0,"",0,"","",
        0,Cartoon(0,"","","","",0,0,0, Genres(0,"","","",""),
            Creator(0,0,"","","",
                User(0,"","","","",0)))),
        )
    var commentItems by remember { mutableStateOf(initialcomment) }
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
                showAllData(commentList,contextForToast,epid,cartoonid)
                println(epid)
                println(cartoonid)
                println()
            }
        }
    }
    Column {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ){
            Box(
                modifier = Modifier
                    .padding(start = 15.dp,top = 15.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.user),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .clip(CircleShape)
                        .border(
                            width = 2.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(100.dp)
                        )
                        .size(50.dp)
                        .padding(top = 10.dp)
                )
            }
            Spacer(modifier = Modifier .padding(start = 25.dp, top = 100.dp))
            OutlinedTextField(
                value = textFieldComment,
                onValueChange = {textFieldComment = it},
                label = { Text(text = "Comment", 
                    modifier = Modifier
                        .padding(top = 12.dp))
                        },
                trailingIcon = { Icon(imageVector = Icons.Default.Edit,
                    contentDescription = null)},
                modifier = Modifier
                    .width(200.dp)
                    .height(100.dp)
                    .padding(top = 10.dp)
            )
        }
        Spacer(modifier = Modifier.padding(top = 10.dp))
        Row (
            modifier = Modifier
                .padding(start = 219.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Box(
                Modifier
                    .clickable
                    {
                        createClient.insertComment(textFieldComment,userId,epid)
                            .enqueue(object : Callback<commentdata>{
                                override fun onResponse(call: Call<commentdata>, response: Response<commentdata>) {
                                    if(response.isSuccessful){
                                        textFieldComment = ""
                                    }
                                }
                                override fun onFailure(call: Call<commentdata>, t: Throwable) {
                                    Toast.makeText(contextForToast,"Cant Upload Comment To Server", Toast.LENGTH_LONG).show()
                                }
                            })
                        if (navController.currentBackStack.value.size >= 2){
                            navController.popBackStack()
                        }
                        navController.navigate(EpisodeBottom.Comment.route)
                    }
                    .height(30.dp)
                    .width(70.dp)
            ) {
                Row {
                    Text(text = "Sent")
                    Spacer(modifier = Modifier .padding(start = 8.dp))
                    Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = null)
                }
            }
        }
        Spacer(modifier = Modifier .padding(top = 50.dp))
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ){
                itemsIndexed(items = commentList,
                ){index,item->
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 8.dp)
                            .fillMaxWidth()
                            .height(100.dp)
                            .border(
                                width = 2.dp,
                                color = Color.Gray,
                                shape = getBottomLineShape(2.dp)
                            ),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White,
                        ),
                    ){
                        Row (
                            Modifier
                                .fillMaxWidth()
                                .height(Dp(120f))
                                .padding(10.dp)
                        ){
                            Text(
                                text ="User ID : ${item.userId}\n"
                                        +"Episode : ${item.episode.epNumber}\n"
                                        +"Comment : ${item.content}",
                                Modifier.weight(0.85f)
                            )
                        }
                    }
                }
            }
    }
}
@Composable
fun getBottomLineShape(lineThicknessDp: Dp) : Shape {
    val lineThicknessPx = with(LocalDensity.current) {lineThicknessDp.toPx()}
    return GenericShape { size, _ ->
        // 1) Bottom-left corner
        moveTo(0f, size.height)
        // 2) Bottom-right corner
        lineTo(size.width, size.height)
        // 3) Top-right corner
        lineTo(size.width, size.height - lineThicknessPx)
        // 4) Top-left corner
        lineTo(0f, size.height - lineThicknessPx)
    }
}

fun showAllData(studentItemList:MutableList<commentdata>,context: Context,epid: Int,cartoonid:Int){
    val createClient = API.create()
    createClient.getEpcommnet(cartoonid,epid)
        .enqueue(object : Callback<List<commentdata>> {
            override fun onResponse(call: Call<List<commentdata>>,
                                    response: Response<List<commentdata>>
            ) {
                studentItemList.clear()
                response.body()?.forEach {
                    studentItemList.add(commentdata(it.content,it.userId,it.episodeId,it.episode))
                    Log.i("episodeID",it.episode.toString())
                }
            }
            override fun onFailure(call: Call<List<commentdata>>, t: Throwable) {
                Toast.makeText(context,"ShowFailonFailure"+t.message,
                    Toast.LENGTH_LONG).show()
            }
        })
}
