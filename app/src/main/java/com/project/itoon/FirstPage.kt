package com.project.itoon

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.project.itoon.ui.theme.ITOONTheme

@Preview(showBackground = true)
@Composable
fun FirstPagePreview() {
    ITOONTheme {
        FirstPage()
    }
}


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

    Column(modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Box(modifier = modifier.wrapContentSize()){
            HorizontalPager(state = pagerState,
            ){

            }
        }
    }
}


@Composable
fun FirstPage(){

}