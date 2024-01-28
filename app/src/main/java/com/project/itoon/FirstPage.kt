@file:OptIn(ExperimentalFoundationApi::class)

package com.project.itoon

import android.widget.Toast
import androidx.compose.animation.rememberSplineBasedDecay

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
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

data class CartoonHitData(val cartoonImage: String,val cartoonName: String, val cartoonGenre: String)



private fun prepareCartoonList(): MutableList<CartoonHitData>{
    val cartoonHitList  = mutableListOf<CartoonHitData>()
    val cartoonList = listOf(
        listOf("https://manhwatop.com/wp-content/uploads/2023/03/In-My-Death-I-Became-My-Brothers-Regret-193x278.jpg", "In My Death, I Became My Brother’s Regret","Action"),
        listOf("https://reapertrans.com/wp-content/uploads/2024/01/A-Thought-Of-Freedom.jpg", "A Thought Of Freedom", "Action"),
        listOf("https://reapertrans.com/wp-content/uploads/2023/12/Locked-Up.png", "Locked Up", "Adult"),
        listOf("https://reapertrans.com/wp-content/uploads/2024/01/a-delicate-relationship.jpg", "A Delicate Relationship", "Adult"),
        listOf("https://reapertrans.com/wp-content/uploads/2023/01/return-of-the-frozen-player-1.jpg", "Return of the Frozen Player", "Fastasy"),
        listOf("https://reapertrans.com/wp-content/uploads/2023/02/My-Civil-Servant-Life-Reborn-in-the-Strange-WorldMage.jpg", "My Civil Servant Life Reborn in the Strange World", "Fastasy"),
        listOf("https://reapertrans.com/wp-content/uploads/2023/02/Mato-Seihei-no-Slave.jpg", "Mato Seihei no Slave", "Shonen"),
        listOf("https://reapertrans.com/wp-content/uploads/2023/01/Reaper-of-the-Drifting-Moon.jpg", "Reaper of the Drifting Moon", "Action-fastasy"),
    )

    for (cartoonArr in cartoonList) {
        cartoonHitList.add(
            CartoonHitData(
                cartoonImage = cartoonArr[0] as String,
                cartoonName = cartoonArr[1] as String,
                cartoonGenre = cartoonArr[2] as String
            )
        )
    }
    return cartoonHitList
}
private fun prepareCartoonHitList(): MutableList<CartoonHitData>{
    val cartoonHitList = mutableListOf<CartoonHitData>()
    val cartoonList = listOf(
        listOf("https://manhwatop.com/wp-content/uploads/2023/03/In-My-Death-I-Became-My-Brothers-Regret-193x278.jpg", "In My Death, I Became My Brother’s Regret","Action"),
        listOf("https://reapertrans.com/wp-content/uploads/2024/01/A-Thought-Of-Freedom.jpg", "A Thought Of Freedom", "Action"),
        listOf("https://reapertrans.com/wp-content/uploads/2023/12/Locked-Up.png", "Locked Up", "Adult"),
        listOf("https://reapertrans.com/wp-content/uploads/2024/01/a-delicate-relationship.jpg", "A Delicate Relationship", "Adult"),
        listOf("https://reapertrans.com/wp-content/uploads/2023/01/return-of-the-frozen-player-1.jpg", "Return of the Frozen Player", "Fastasy"),
    )
    for (cartoonArr in cartoonList) {
        cartoonHitList.add(
            CartoonHitData(
                cartoonImage = cartoonArr[0] as String,
                cartoonName = cartoonArr[1] as String,
                cartoonGenre = cartoonArr[2] as String
            )
        )
    }
    return cartoonHitList
}

private fun prepareCartoonAdultList(): MutableList<CartoonHitData>{
    val cartoonListAdult  = mutableListOf<CartoonHitData>()
    val cartoonList = listOf(
        listOf("https://reapertrans.com/wp-content/uploads/2024/01/a-delicate-relationship.jpg", "A Delicate Relationship", "Adult"),
        listOf("https://reapertrans.com/wp-content/uploads/2024/01/a-delicate-relationship.jpg", "A Delicate Relationship", "Adult"),
        listOf("https://reapertrans.com/wp-content/uploads/2024/01/a-delicate-relationship.jpg", "A Delicate Relationship", "Adult"),
        listOf("https://reapertrans.com/wp-content/uploads/2024/01/a-delicate-relationship.jpg", "A Delicate Relationship", "Adult"),
        listOf("https://reapertrans.com/wp-content/uploads/2024/01/a-delicate-relationship.jpg", "A Delicate Relationship", "Adult"),
    )
    for (cartoonArr in cartoonList) {
        cartoonListAdult.add(
            CartoonHitData(
                cartoonImage = cartoonArr[0] as String,
                cartoonName = cartoonArr[1] as String,
                cartoonGenre = cartoonArr[2] as String
            )
        )
    }
    return cartoonListAdult
}

private fun prepareCartoonFastasyList(): MutableList<CartoonHitData>{
    val cartoonListFastasy  = mutableListOf<CartoonHitData>()
    val cartoonList = listOf(
        listOf("https://reapertrans.com/wp-content/uploads/2023/02/My-Civil-Servant-Life-Reborn-in-the-Strange-WorldMage.jpg", "My Civil Servant Life Reborn in the Strange World", "Fastasy"),
        listOf("https://reapertrans.com/wp-content/uploads/2023/02/My-Civil-Servant-Life-Reborn-in-the-Strange-WorldMage.jpg", "My Civil Servant Life Reborn in the Strange World", "Fastasy"),
        listOf("https://reapertrans.com/wp-content/uploads/2023/02/My-Civil-Servant-Life-Reborn-in-the-Strange-WorldMage.jpg", "My Civil Servant Life Reborn in the Strange World", "Fastasy"),
        listOf("https://reapertrans.com/wp-content/uploads/2023/02/My-Civil-Servant-Life-Reborn-in-the-Strange-WorldMage.jpg", "My Civil Servant Life Reborn in the Strange World", "Fastasy"),
        listOf("https://reapertrans.com/wp-content/uploads/2023/02/My-Civil-Servant-Life-Reborn-in-the-Strange-WorldMage.jpg", "My Civil Servant Life Reborn in the Strange World", "Fastasy"),
    )
    for (cartoonArr in cartoonList) {
        cartoonListFastasy.add(
            CartoonHitData(
                cartoonImage = cartoonArr[0] as String,
                cartoonName = cartoonArr[1] as String,
                cartoonGenre = cartoonArr[2] as String
            )
        )
    }
    return cartoonListFastasy
}

@Composable
fun showTextTest(textFor: String){
    val context = LocalContext.current
    Toast.makeText(context,"$textFor is a value from Onclick!!",Toast.LENGTH_SHORT).show()
}



val fillMaxwidht = "Modifier.fillMaxWidth()"

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
                            painter = rememberAsyncImagePainter(item.cartoonImage) ,
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(RoundedCornerShape(5.dp))
                                .fillMaxSize()
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
    val cartoonList = prepareCartoonHitList()
        Column {
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
                            painter = rememberAsyncImagePainter(item.cartoonImage),
                            contentDescription = item.cartoonName,
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
                                text="${item.cartoonName}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                lineHeight = 3.sp,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Text(
                                text="${item.cartoonGenre}",
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

@Composable
fun CartoonAdultColumn(){
    val cartoonList = prepareCartoonAdultList()
    Box(
        modifier = Modifier.fillMaxWidth()
    ){
        Column {
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
                            painter = rememberAsyncImagePainter(item.cartoonImage),
                            contentDescription = item.cartoonName,
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
                                text="${item.cartoonName}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                lineHeight = 3.sp,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Text(
                                text="${item.cartoonGenre}",
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
fun CartoonFastasyColumn(){
    val cartoonList = prepareCartoonFastasyList()
    Box(
        modifier = Modifier.fillMaxWidth()
    ){
        Column {
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
                            painter = rememberAsyncImagePainter(item.cartoonImage),
                            contentDescription = item.cartoonName,
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
                                text="${item.cartoonName}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black,
                                lineHeight = 3.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.width(300.dp)
                            )
                            Text(
                                text="${item.cartoonGenre}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Gray,
                                maxLines = 1,
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
fun CartoonHit(){
    val pagerState = rememberPagerState(pageCount = {
        3
    })

    var textHead by remember { mutableStateOf("") }

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 12.dp, bottom = 5.dp, start = 15.dp, end = 15.dp)
    ){
        Text(
            text = "$textHead",
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
            HorizontalPager(state = pagerState,
            ){
                currentPage ->

                Card(
                    Modifier
                        .wrapContentSize()
                        .background(color = Color.Transparent)
                    ,
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent
                    )
                    )
                {
                    when(currentPage){
                        0 -> {
                            CartoonHitColumn()
                            textHead = "เรื่องฮิต"
                        }
                        1 -> {
                            CartoonAdultColumn()
                            textHead = "เรื่องผู้ใหญ่ยอดฮิต"
                        }

                        2 -> {
                            CartoonFastasyColumn()
                            textHead = "เรื่องแฟนตาซียอดฮิต"
                        }
                    }
                }
            }
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
        //topappbar

        //Slide image
        SliderImage()

        //Hit story
        CartoonHit()

        //New Cartoon hit
        NewCartoonHit()


        //

    }
}