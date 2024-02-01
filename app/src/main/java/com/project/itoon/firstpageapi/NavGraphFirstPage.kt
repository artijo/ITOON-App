package com.project.itoon.firstpageapi

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.project.itoon.FirstPage
import com.project.itoon.GenrePage

@Composable
fun NavGraphFirstPage(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = FirstPageSealClass.FirstPage.route
    ){
        composable(
            route = FirstPageSealClass.FirstPage.route
        ){
            Box(Modifier.padding(top = 55.dp)){
                FirstPage()
            }
        }
        composable(
            route = FirstPageSealClass.Genre.route
        ){
            GenrePage()
        }
    }
}