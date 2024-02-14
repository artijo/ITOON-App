package com.project.itoon.Commentpage

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.project.itoon.API
import com.project.itoon.R
import com.project.itoon.ui.theme.ITOONTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun CommentPage(navController: NavHostController){
    var textFieldComment by remember{ mutableStateOf("") }
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
                createClient.getComment().enqueue(
                    object : Callback<List<commentdata>> {
                        @SuppressLint("RestrictedApi")
                        override fun onResponse(
                            call: Call<List<commentdata>>,
                            response: Response<List<commentdata>>
                        ) {
                            if (response.isSuccessful){
                                commentList.clear()
                                response.body()?.forEach {
                                    commentList.add(commentdata(it.episodeId,it.userId,it.content))
                                }
                            }else{

                            }
                        }
                        override fun onFailure(call: Call<List<commentdata>>, t: Throwable) {
                        }
                    }
                )
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
                        //TODO///////////
                        textFieldComment = ""
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
                                        +"Episode : ${item.episodeId}\n"
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