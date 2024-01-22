package com.project.itoon

import android.content.Context

import android.widget.Toast

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridItemInfo
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.itoon.ui.theme.ITOONTheme
import kotlinx.coroutines.delay
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
        R.drawable.who_made_me_a_princess,
        R.drawable.lout_of_count_family,
        R.drawable.the_beloved_little_princess,
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
                Card(
                    modifier
                        .wrapContentSize()
                        .height(300.dp),
                    shape = RoundedCornerShape(0.dp),
                    elevation = CardDefaults.cardElevation(0    .dp),
                ){
                    Image(
                        painter = painterResource(id = images[currentPage]),
                        contentDescription = "",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

            }
        }
    }
}
//sliderImage ***************************************

//cartoonHit ****************************************

data class CartoonHitzData(val cartoonImage: Int,val cartoonName: String, val cartoonGenre: String)

private fun prepareCartoonList(): MutableList<CartoonHitzData>{
    val cartoonListWow = listOf(
        listOf(R.drawable.who_made_me_a_princess, "Who made me a princess", "Romance"),
        listOf(R.drawable.lout_of_count_family, "Lout of count family", "Action"),
        listOf(R.drawable.the_beloved_little_princess, "The beloved little princess", "Fantasy"),
    )
    val cartoonList  = mutableListOf<CartoonHitzData>()
    for (cartoonArr in cartoonListWow) {
        cartoonList.add(
            CartoonHitzData(
                cartoonImage = cartoonArr[0] as Int,
                cartoonName = cartoonArr[1] as String,
                cartoonGenre = cartoonArr[2] as String
            )
        )
    }
    return cartoonList
}






@Composable
fun CartoonHit(){
    val cartoonListz = prepareCartoonList()
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 10.dp, horizontal = 10.dp)
    ){
        Text(
            text = "HIT STORY",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
    Column(
        Modifier
            .fillMaxSize()
            .padding(vertical = 0.25.dp)
    ){
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(space = 0.dp),
            contentPadding = PaddingValues(horizontal = 10.dp)
        ){
            items(cartoonListz){ item->
                Box(modifier = Modifier
                    .fillMaxSize()
                    .width(128.dp)
                    .height(128.dp)
                    .padding(end = 10.dp)

                ){
                    Image(
                        painter = painterResource(id = item.cartoonImage) ,
                        contentDescription = "",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.clip(RoundedCornerShape(10.dp))
                    )
                    Text(
                        text = "Hello",
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 8.dp),
                        color = Color.White
                    )
                }

            }
        }

    }
}
//Main page of first page...
@Composable
fun FirstPage(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Image slider image
        SliderImage()
        //Header image
        CartoonHit()
    }
}