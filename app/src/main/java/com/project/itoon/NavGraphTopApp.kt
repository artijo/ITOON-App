package com.project.itoon

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraphTopApp(navHostController: NavHostController){
    NavHost(navController = navHostController, startDestination = TopLazyRow.Recently.route){
        composable(route = TopLazyRow.Recently.route){
            Recently()
        }
        composable(route = TopLazyRow.Like.route){
            Like()
        }

        composable(route = TopLazyRow.Bought.route){
            Bought()
        }

        composable(route = TopLazyRow.Creator.route){
            Creator()
        }

        composable(route = TopLazyRow.Comment.route){
            Comment()
        }
    }
}



//@SuppressLint("RestrictedApi")
//@Composable
//fun TopLazyBar(navHostController: NavHostController){
//    val TopNavItem = listOf(
//        TopLazyRow.Recently,
//        TopLazyRow.Like,
//        TopLazyRow.Bought,
//        TopLazyRow.Creator,
//        TopLazyRow.Comment
//    )
//    var topnavselectScreen by remember {
//        mutableStateOf(0)
//    }
//    NavigationBar(modifier = Modifier.background(color = Color.Blue)) {
//        TopNavItem.forEachIndexed { index, topLazyRow ->
//            NavigationBarItem(selected = (topnavselectScreen==index),
//                onClick = {
//                    if(navHostController.currentBackStack.value.size>=2){
//                        navHostController.popBackStack()
//                    }
//                    topnavselectScreen = index
//                    navHostController.navigate(topLazyRow.route)
//                }, icon = { null }, label = { Text(text = topLazyRow.name)})
//        }
//    }
//}


