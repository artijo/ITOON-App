package com.project.itoon.TopLazyRow

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.project.itoon.ShowTextTest
import com.project.itoon.firstpageapi.Cartoon
import com.project.itoon.firstpageapi.CartoonAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun Recently(navHostController: NavHostController){
    val createClient = CartoonAPI.create()
    val cartoonList  = remember {
        mutableStateListOf<Cartoon>()
    }
    val contextForToast = LocalContext.current.applicationContext

    cartoonList.clear()
    createClient.retrieveCartoon()
        .enqueue(object :Callback<List<Cartoon>>{
            override fun onResponse(call: Call<List<Cartoon>>, response: Response<List<Cartoon>>) {
                response.body()?.forEach {
                    cartoonList.add(Cartoon(it.id, it.name, it.description, it.releaseDate, it.thumbnail, it.totalEpisodes, it.creatorId, it.genreId, it.genres
                    ,it.creator))
                }
            }

            override fun onFailure(call: Call<List<Cartoon>>, t: Throwable) {
                Toast.makeText(contextForToast,"Error on Failure"+t.message,Toast.LENGTH_LONG).show()
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
            .fillMaxSize()
            .border(5.dp, Color.Magenta),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {

        Text(text = "RecentlyPage")
        LazyColumn(verticalArrangement = Arrangement.Center,
            contentPadding = PaddingValues(horizontal = 15.dp)
        ){
            items(cartoonList){
                    item ->
                Row (
                    modifier = Modifier
                        .width(360.dp)
                        .padding(bottom = 10.dp)
                ){
                    Image(
                        painter = rememberAsyncImagePainter(item.thumbnail),
                        contentDescription = item.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(45.dp)
                            .height(45.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )
                    Spacer(modifier = Modifier.width(width = 10.dp))
                    Column(
                        Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text= item.name,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            lineHeight = 3.sp,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text= item.genres.name,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            lineHeight = 1.sp,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }

    }
}

