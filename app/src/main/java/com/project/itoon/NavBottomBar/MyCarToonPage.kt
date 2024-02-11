package com.project.itoon.NavBottomBar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.project.itoon.MenuLazyrow
import com.project.itoon.TopLazyRow.NavGraphTopApp

@Composable
fun MyCarToonPage(){
    val contextForToast = LocalContext.current.applicationContext
    var navHostController = rememberNavController()
    Scaffold(
        topBar = { MenuLazyrow(navHostController) }
    ) {
        paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NavGraphTopApp(navHostController = navHostController)
        }
    }
}