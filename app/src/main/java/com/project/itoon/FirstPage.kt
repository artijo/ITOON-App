package com.project.itoon

import android.content.Context

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
import androidx.compose.foundation.layout.fillMaxHeight

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridItemInfo
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState

import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
                    elevation = CardDefaults.cardElevation(0.dp),

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

//@Composable
//private fun CartoonHitzLayout(
//    cartoonHitzList: CartoonHitzData,
//    context: Context = LocalContext.current.applicationContext,
//){
//    LazyVerticalGrid(
//        modifier = Modifier.fillMaxSize(),
//        columns = GridCells.Fixed(count = 5),
//        verticalArrangement = Arrangement.spacedBy(space = 10.dp),
//        horizontalArrangement = Arrangement.spacedBy(space = 10.dp),
//    ){
//        items(cartoonHitzList){
//
//        }
//    }
//
//
//    Card{
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .clickable {
//                    Toast
//                        .makeText(context, cartoonHitzList.cartoonName, Toast.LENGTH_SHORT)
//                        .show()
//                }
//                .padding(horizontal = 5.dp),
//                verticalAlignment = Alignment.CenterVertically
//        ){
//            Image(
//                painter = painterResource(id = cartoonHitzList.cartoonImage),
//                contentDescription = cartoonHitzList.cartoonName,
//                modifier = Modifier.size(36.dp)
//            )
//            Spacer(modifier = Modifier.width(width = 12.dp))
//            Column(modifier = Modifier.padding(vertical = 5.dp)) {
//                Text(text = cartoonHitzList.cartoonName, fontSize = 14.sp)
//                Text(text = cartoonHitzList.cartoonGenre, fontSize = 14.sp)
//            }
//        }
//    }
//}



@Composable
fun CartoonHitzList(){
    val cartoonHitzList = prepareCartoonList()
//    Column(
//        verticalArrangement = Arrangement.spacedBy(10.dp),
//        modifier = Modifier.padding(10.dp))
//    {
//
//    }
    LazyHorizontalGrid(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(space = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 16.dp),
        rows = GridCells.Fixed(count = 2) ,
        contentPadding = PaddingValues(all = 10.dp),
    ){
        items(cartoonHitzList.size){ index ->
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(width = 100.dp),
                contentAlignment = Alignment.Center
            ){
                Image(
                    painter = painterResource(id = cartoonHitzList[index].cartoonImage),
                    contentDescription = null,
                )
                Text(text = "${cartoonHitzList[index].cartoonGenre}")
                Text(text = "${cartoonHitzList[index].cartoonName}")
            }
        }
    }

}

@Composable
fun CartoonHit(){
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)){
        Text(
            text = "HIT STORY",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
    CartoonHitzList()

}
//cartoonHit ****************************************


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