package com.project.itoon.firstpageapi

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


@SuppressLint("RestrictedApi")
@Composable
private fun MyTopBar(navController : NavHostController){
    val genres =listOf(
        "Fastasy",
        "Action",
        "Romance",
        "Comedy",
        "SciFi",
    )

    var selectedItem by remember {
        mutableIntStateOf(1001)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()

    ){
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(space = 10.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 1.dp),
            modifier = Modifier
                .border(1.dp, Color.Gray)
                .fillMaxWidth()
                .align(alignment = Alignment.TopStart)
        ){
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(1.dp)
                        .clickable {
                            if (navController.currentBackStack.value.size >= 2) {
                                navController.popBackStack()
                            }
                            selectedItem = 1001
                            navController.navigate(FirstPageSealClass.FirstPage.route)
                        }
                        .drawBehind {
                            val borderSize = 2.dp.toPx()
                            if (selectedItem == 1001) {
                                drawLine(
                                    color = Color.Gray,
                                    start = Offset(0f, size.height),
                                    end = Offset(size.width, size.height),
                                    strokeWidth = borderSize
                                )
                            }
                        }
                ){
                    Text(
                        text = "Main Page",
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
            itemsIndexed(items = genres){ index, item ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(1.dp)
                        .clickable {
                            if (navController.currentBackStack.value.size >= 2) {
                                navController.popBackStack()
                            }
                            selectedItem = index
                            navController.navigate(FirstPageSealClass.Genre.route)

                        }
                        .drawBehind {
                            val borderSize = 2.5.dp.toPx()
                            if (selectedItem == index) {
                                drawLine(
                                    color = Color.Gray,
                                    start = Offset(0f, size.height),
                                    end = Offset(size.width, size.height),
                                    strokeWidth = borderSize
                                )
                            }
                        }
                ){
                    Text(
                        text = item,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun TestCall(){
    val navController = rememberNavController()
    Scaffold(
        topBar = { MyTopBar(navController = navController)},
        floatingActionButtonPosition = FabPosition.Start
    ) {paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
        }
        NavGraphFirstPage(navController = navController)
    }


}