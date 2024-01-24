package com.project.itoon

import android.widget.Toast

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
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
        "https://citly.me/drgAT",
        "https://citly.me/7ShLx",
        "https://citly.me/XQxzp",
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
                        elevation = CardDefaults.cardElevation(5.dp),
                    ){
                        Image(
                            painter =  rememberAsyncImagePainter(images[currentPage]),
                            contentDescription = "Image ${currentPage+1}",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Box(
                        modifier = Modifier
                            .padding(5.dp)
                            .background(
                                color = Color(208, 208, 208),
                                shape = RoundedCornerShape(50.dp)
                            )
                            .padding(vertical = 1.dp, horizontal = 10.dp)
                            .align(Alignment.BottomEnd)
                    ){
                        Text(
                            text = "${currentPage+1} / ${images.size}",
                            fontSize = 14.sp,
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

data class CartoonHitzData(val cartoonImage: Int,val cartoonName: String, val cartoonGenre: String)

private fun prepareCartoonList(): MutableList<CartoonHitzData>{
    val cartoonListWow = listOf(
        listOf(R.drawable.who_made_me_a_princess, "Who made me a princess", "Romance"),
        listOf(R.drawable.lout_of_count_family, "Lout of count family", "Action"),
        listOf(R.drawable.who_made_me_a_princess, "Who made me a princess", "Romance"),
        listOf(R.drawable.the_beloved_little_princess, "The beloved little princess", "Fantasy"),
        listOf(R.drawable.who_made_me_a_princess, "Who made me a princess", "Romance"),
        listOf(R.drawable.lout_of_count_family, "Lout of count family", "Action"),
        listOf(R.drawable.the_beloved_little_princess, "The beloved little princess", "Fantasy"),
        listOf(R.drawable.who_made_me_a_princess, "Who made me a princess", "Romance"),
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
fun showTextTest(textFor: String){
    val context = LocalContext.current
    Toast.makeText(context,"$textFor is a value from Onclick!!",Toast.LENGTH_SHORT).show()
}


@Composable
fun NewCartoonHit(){
    val cartoonList = prepareCartoonList()
    var isOpen by remember { mutableStateOf(false) }
    var idTextCartoon = remember { mutableStateOf("") }
    if(isOpen){
        showTextTest(textFor = idTextCartoon.value)
        isOpen = false
    }
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 12.dp, horizontal = 15.dp)
    ){
        Text(
            text = "New hit cartoon",
            fontSize = 18.sp,
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
                                idTextCartoon.value = item.cartoonName
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
                            painter = painterResource(id = item.cartoonImage) ,
                            contentDescription = "",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.clip(RoundedCornerShape(5.dp))
                        )
                    }
                    Text(
                        text="${item.cartoonGenre}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray,
                        modifier = Modifier.padding(top=100.dp)
                    )
                    Text(
                        text = "${item.cartoonName}",
                        fontSize = 14.sp,
                        color = Color.Black,
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
private fun CartoonHitLayout(
    cartoonList:CartoonHitzData
){
    val images = listOf(
        R.drawable.who_made_me_a_princess,
        R.drawable.lout_of_count_family,
        R.drawable.the_beloved_little_princess,
    )
}


@Composable
fun CartoonHit(){
    val cartoonList = prepareCartoonList()
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 12.dp, horizontal = 15.dp)
    ){
        Text(
            text = "Hit cartoon",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ){

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

        //Slide image
        SliderImage()

        //Hit story
        CartoonHit()

        //New Cartoon hitz
        NewCartoonHit()
    }
}