package com.project.itoon

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = BottomBar.Favorite.route) {
        composable(route = BottomBar.Favorite.route) {
            FirstPage(navHostController)
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
    }
}

@SuppressLint("RestrictedApi")
@Composable
fun MyBottomBar(navHostController: NavHostController, contextForToast: Context){
    val navigationItems = listOf(
        BottomBar.Favorite,
        BottomBar.MyCartoon,
        BottomBar.Coin,
        BottomBar.ETC,
    )
    var selectScreen by remember {
        mutableStateOf(0)
    }
    NavigationBar(modifier = Modifier.background(color = Color.Green)) {
        navigationItems.forEachIndexed { index, bottomBar ->
            NavigationBarItem(selected = (selectScreen==index),
                onClick = {
                    if (navHostController.currentBackStack.value.size>=4){
                        navHostController.popBackStack()
                    }
                    selectScreen = index
                    navHostController.navigate(bottomBar.route)
                }, label = { Text(text = bottomBar.name) } ,icon = {
                    Icon(painter = painterResource(id = bottomBar.icon),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    ) })

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(navController: NavHostController, contextForToast: Context){
    TopAppBar(
        title = { Row(verticalAlignment = Alignment.CenterVertically) {
//            Icon(painter = painterResource(id = R.drawable.logo), contentDescription = null
//            ,tint = Color.Unspecified)
            Text(text = "ITOON", letterSpacing = 1.75.sp, fontWeight = FontWeight.SemiBold)
        }
        }, actions = {
            IconButton(onClick = { navController.navigate(BottomBar.Favorite.route) },modifier = Modifier.size(20.dp)) {
                Image(painter = painterResource(id = R.drawable.favorite), contentDescription = null)
            }
            IconButton(onClick = { navController.navigate(BottomBar.Favorite.route) },modifier = Modifier.size(20.dp)) {
                Image(painter = painterResource(id = R.drawable.favorite), contentDescription = null)
            }
        }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(184,0,0)))
}
