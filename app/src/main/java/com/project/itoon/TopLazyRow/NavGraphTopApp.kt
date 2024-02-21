package com.project.itoon.TopLazyRow

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.project.itoon.TopLazyRow.Bought
import com.project.itoon.TopLazyRow.Comment
import com.project.itoon.TopLazyRow.Creator
import com.project.itoon.TopLazyRow.Like
import com.project.itoon.TopLazyRow.Recently
import com.project.itoon.TopLazyRow.TopLazyRow

@Composable
fun NavGraphTopApp(navHostController: NavHostController){

    NavHost(navController = navHostController, startDestination = TopLazyRow.Recently.route){
        composable(route = TopLazyRow.Recently.route){
            Recently(navHostController)
        }
        composable(route = TopLazyRow.Like.route){
            Like(navHostController)
        }

        composable(route = TopLazyRow.Bought.route){
            Bought(navHostController)
        }

        composable(route = TopLazyRow.Creator.route){
            Creator(navHostController)
        }

        composable(route = TopLazyRow.Comment.route){
            Comment(navHostController)
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


