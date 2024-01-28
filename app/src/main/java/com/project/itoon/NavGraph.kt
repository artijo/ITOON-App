package com.project.itoon

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = BottomBar.FirstPage.route) {
        composable(route = BottomBar.Favorite.route) {
            FavoritePage()
        }

        composable(route = BottomBar.MyCartoon.route) {
            MyCarToonPage()
        }

        composable(route = BottomBar.Coin.route) {
            CoinPage()
        }
        composable(route = BottomBar.ETC.route) {
            ETCPage()
        }
        composable(route = BottomBar.FirstPage.route){
            FirstPage()
        }
    }
}