@file:OptIn(ExperimentalFoundationApi::class)

package com.project.itoon

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi

import androidx.compose.foundation.Image
import androidx.compose.foundation.background

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
import androidx.compose.foundation.layout.wrapContentSize

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

import androidx.compose.ui.graphics.Color

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.project.itoon.firstpageapi.Cartoon
import com.project.itoon.firstpageapi.CartoonAPI
import com.project.itoon.firstpageapi.CartoonRec
import com.project.itoon.firstpageapi.Genres
import com.project.itoon.ui.theme.ITOONTheme
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Objects
import androidx.compose.foundation.layout.Box as Box

@Preview(showBackground = true)
@Composable
fun FirstPagePreview() {
    ITOONTheme {
        FirstPage()
    }
}


//sliderImage ****************************************
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SliderImage(modifier: Modifier = Modifier){
    val images = listOf(
        "https://reapertrans.com/wp-content/uploads/2023/01/Reaper-of-the-Drifting-Moon.png",
        "https://citly.me/7ShLx",
        "https://reapertrans.com/wp-content/uploads/2023/08/Martial-God-Regressed-to-Level-2-slide.png",
    )

    val pagerState = rememberPagerState(pageCount = {
        images.size
    })

    LaunchedEffect(Unit){
        while (true){
            delay(3000)
            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            pagerState.scrollToPage(nextPage)
        }
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Box(modifier = modifier.wrapContentSize()){
            HorizontalPager(state = pagerState,
            ){
                currentPage ->
                Box(modifier = Modifier.fillMaxSize()){
                    Card(
                        modifier
                            .wrapContentSize()
                            .height(200.dp),
                        shape = RoundedCornerShape(0.dp),
                        elevation = CardDefaults.cardElevation(0.dp),
                    ){
                        Image(
                            painter =  rememberAsyncImagePainter(images[currentPage]),
                            contentDescription = "Image ${currentPage+1}",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxSize()
                        )
                    }
                    Box(
                        modifier = Modifier
                            .padding(end = 5.dp)
                            .align(Alignment.BottomEnd)
                    ){
                        Text(
                            text = "${currentPage+1} / ${images.size}",
                            fontSize = 12.sp,
                            color = Color.White,
                        )
                    }
                }
            }
        }
    }
}
//sliderImage ***************************************





//cartoonHit ****************************************






@Composable
fun showTextTest(textFor: String){
    val context = LocalContext.current
    Toast.makeText(context,"$textFor is a value from Onclick!!",Toast.LENGTH_SHORT).show()
}

fun getGenreName(genres: List<Genres>): String {
    // Assuming Genre has a property called 'name'
    return genres.joinToString { it.name }
}

@Composable
fun NewCartoonHit(){
    val createClient = CartoonAPI.create()
    val cartoonList = remember { mutableStateListOf<Cartoon>() }
    val contextForToast = LocalContext.current.applicationContext
//    Query data from API
    cartoonList.clear()
    createClient.retrieveCartoon()
        .enqueue(object: Callback<List<Cartoon>>{
            override fun onResponse(call: Call<List<Cartoon>>,
                                    response: Response<List<Cartoon>>
            ){
             response.body()?.forEach{
                 cartoonList.add(Cartoon(it.id , it.name, it.description, it.releaseDate, it.thumbnail, it.totalEpisodes, it.creatorId, it.genreId, it.genres))
             }
            }

            override fun onFailure(call: Call<List<Cartoon>>, t: Throwable) {
                Toast.makeText(contextForToast,"Error on Failure" + t.message, Toast.LENGTH_LONG).show()
            }
    })



//    Open Toast Funtion  for test
    var isOpen by remember { mutableStateOf(false) }
    val idTextCartoon = remember { mutableStateOf("") }
    if(isOpen){
        showTextTest(textFor = idTextCartoon.value)
        isOpen = false
    }
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 12.dp, bottom = 5.dp, start = 15.dp, end = 15.dp)
    ){
        Text(
            text = "เรื่องใหม่มาแรง",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
    Column(
        Modifier
            .fillMaxSize()
    ){
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(space = 0.dp),
            contentPadding = PaddingValues(horizontal = 15.dp)
        ){
            items(cartoonList){ item->
                Box(
                    Modifier
                        .fillMaxSize()
                        .clickable(
                            onClick = {
                                isOpen = true
                                idTextCartoon.value = item.name
                            }
                        )
                ){
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .width(110.dp)
                        .height(100.dp)
                        .padding(end = 10.dp)

                    ){
                        Image(
                            painter = rememberAsyncImagePainter(item.thumbnail) ,
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(RoundedCornerShape(5.dp))
                                .fillMaxSize()
                        )
                    }
                    Text(
                        text= item.genres.name,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray,
                        modifier = Modifier.padding(top=100.dp)
                    )
                    Text(
                        text = item.name,
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium,
                        maxLines = 2,
                        style = LocalTextStyle.current.copy(lineHeight = 15.sp),
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(top = 118.dp)
                            .width(100.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CartoonHitColumn(){
    val createClient = CartoonAPI.create()
    val cartoonList = remember { mutableStateListOf<CartoonRec>() }
    val contextForToast = LocalContext.current.applicationContext
//    Query data from API
    cartoonList.clear()
    createClient.recCartoon()
        .enqueue(object: Callback<List<CartoonRec>>{
            override fun onResponse(call: Call<List<CartoonRec>>,
                                    response: Response<List<CartoonRec>>
            ){
                response.body()?.forEach{
                    cartoonList.add(CartoonRec(it.id , it.name, it.description, it.releaseDate, it.thumbnail, it.totalEpisodes, it.creatorId, it.genreId, it.genres))
                }
            }

            override fun onFailure(call: Call<List<CartoonRec>>, t: Throwable) {
                Toast.makeText(contextForToast,"Error on Failure" + t.message, Toast.LENGTH_LONG).show()
            }
        })
        Column{
            cartoonList.forEach{ item ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    Row(
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




@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CartoonRecommend(){
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 12.dp, bottom = 5.dp, start = 15.dp, end = 15.dp)
    ){
        Text(
            text = "เรื่องแนะนำ",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Box(
            modifier = Modifier
                .wrapContentSize()
                .fillMaxSize()
                .padding(horizontal = 15.dp)
        ){
            CartoonHitColumn()
        }
    }
}



//Main page of first page...
@Composable
fun FirstPage(){
    val contextForToast = LocalContext.current.applicationContext
    val navHostController = rememberNavController()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        //Slide image
        SliderImage()

        //Hit story
        CartoonRecommend()

        //New Cartoon hit
        NewCartoonHit()


    }
}