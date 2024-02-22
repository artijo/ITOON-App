@file:OptIn(ExperimentalFoundationApi::class)

package com.project.itoon

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.mutableIntStateOf
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
import com.project.itoon.NavBottomBar.BottomBar
import com.project.itoon.cartoonPage.CartoonPage
import com.project.itoon.firstpageapi.Cartoon
import com.project.itoon.firstpageapi.CartoonAPI
import kotlinx.coroutines.delay
import okhttp3.HttpUrl.Companion.toHttpUrl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



//sliderImage ****************************************
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SliderImage(modifier: Modifier = Modifier){
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


@Composable
private fun CartoonRecommend(navHostController:NavHostController){
    val createClient = CartoonAPI.create()
    val cartoonList = remember { mutableStateListOf<Cartoon>() }
    val contextForToast = LocalContext.current.applicationContext
    var isOpen by remember { mutableStateOf(false) }
    val idTextCartoon = remember { mutableStateOf("") }
    if(isOpen){
        ShowTextTest(textFor = idTextCartoon.value)
        isOpen = false
    }
//    Query data from API
    LaunchedEffect(true){
        createClient.recAllCartoon()
            .enqueue(object: Callback<List<Cartoon>>{
                override fun onResponse(call: Call<List<Cartoon>>,
                                        response: Response<List<Cartoon>>
                ){
                    response.body()?.forEach{
                        cartoonList.add(Cartoon(it.id , it.name, it.description, it.releaseDate, it.thumbnail, it.totalEpisodes, it.creatorId, it.genreId, it.genres,
                            it.creator, it.paid, it.price))
                    }
                }

                override fun onFailure(call: Call<List<Cartoon>>, t: Throwable) {
                    Toast.makeText(contextForToast,"Error on Failure" + t.message, Toast.LENGTH_LONG).show()
                }
        })
    }
    Column {
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
            Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ){
            cartoonList.forEach{ item ->
                var clickCartoon : Cartoon
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp)
                        .clickable(
                            onClick = {
                                clickCartoon = item
                                navHostController.currentBackStackEntry?.savedStateHandle?.set(
                                    "data",
                                    clickCartoon
                                )
                                navHostController.navigate(CartoonPage.CartoonEP.route)
                            }
                        )
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
                            Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp)
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

}



@Composable
fun ShowTextTest(textFor: String){
    val context = LocalContext.current
    Toast.makeText(context,"$textFor is a value from Onclick!!",Toast.LENGTH_SHORT).show()
}


@Composable
private fun NewCartoonHit(navHostController: NavHostController): Int {
    val createClient = CartoonAPI.create()
    val cartoonList = remember { mutableStateListOf<Cartoon>() }
    val contextForToast = LocalContext.current.applicationContext
    LaunchedEffect(true){
        createClient.retrieveCartoon()
            .enqueue(object: Callback<List<Cartoon>>{
                override fun onResponse(call: Call<List<Cartoon>>,
                                        response: Response<List<Cartoon>>
                ){
                    response.body()?.forEach{
                        cartoonList.add(Cartoon(it.id , it.name, it.description, it.releaseDate, it.thumbnail, it.totalEpisodes, it.creatorId, it.genreId, it.genres,
                            it.creator, it.paid, it.price))
                    }
                }

                override fun onFailure(call: Call<List<Cartoon>>, t: Throwable) {
                    Toast.makeText(contextForToast,"Error on Failure" + t.message, Toast.LENGTH_LONG).show()
                }
        })
    }



    var isOpen by remember { mutableStateOf(false) }
    val idTextCartoon = remember { mutableStateOf("") }
    if(isOpen){
        ShowTextTest(textFor = idTextCartoon.value)
        isOpen = false
    }
    Column {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, bottom = 5.dp, start = 15.dp, end = 15.dp)
        ){
            Text(
                text = "การ์ตูนทั้งหมด",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
        Box(Modifier.fillMaxWidth()){
            Column(
                Modifier
                    .fillMaxSize()
            ){
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(space = 0.dp),
                    contentPadding = PaddingValues(horizontal = 15.dp)
                ){
                    var clickCartoon : Cartoon
                    items(cartoonList){ item->
                        val urltext = item.thumbnail
                        Box(
                            Modifier
                                .fillMaxSize()
                                .clickable(
                                    onClick = {
//                                        isOpen = true
//                                        idTextCartoon.value = item.name
                                        Log.i("checkdata",item.toString())

                                        navHostController.navigate( BottomBar.ETC.route)
                                        clickCartoon = item
                                        navHostController.currentBackStackEntry?.savedStateHandle?.set(
                                            "data",
                                            clickCartoon
                                        )
                                        navHostController.navigate(CartoonPage.CartoonEP.route)
                                    }
                                )
                        ){
                            Box(modifier = Modifier
                                .fillMaxSize()
                                .width(110.dp)
                                .height(100.dp)
                                .padding(end = 10.dp)

                            ){
                                if (urltext.startsWith("uploads")){
                                    val replace = urltext.replace("\\","/")
                                    item.thumbnail = "http://10.0.2.2:3000/"+"$replace"
                                    val pathUrl = item.thumbnail.toHttpUrl()
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
                                    println(item.thumbnail)
                                Image(
                                    painter = rememberAsyncImagePainter(item.thumbnail) ,
                                    contentDescription = "",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(5.dp))
                                        .fillMaxSize()
                                )
                                }
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
    }
    return cartoonList.size
}





//Main page of first page...

@Composable
fun FirstPage(navHostController: NavHostController){
    var count by remember {
        mutableIntStateOf(0)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp)
        ){
            SliderImage()
        }
        CartoonRecommend(navHostController)
        count = NewCartoonHit(navHostController)
    }

}