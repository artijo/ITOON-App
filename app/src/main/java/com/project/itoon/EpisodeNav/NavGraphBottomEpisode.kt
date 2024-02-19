package com.project.itoon.EpisodeNav

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
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
import androidx.navigation.compose.currentBackStackEntryAsState
import com.project.itoon.Commentpage.CommentPage
import com.project.itoon.Commentpage.Episode
import com.project.itoon.NavBottomBar.BottomBar
import com.project.itoon.R
import com.project.itoon.cartoonPage.CartoonThisChapter

@Composable
fun NavGraphEpisode(navController:NavHostController,epId:Int,cartoonid:Int){
    NavHost(navController = navController,
        startDestination = EpisodeBottom.Cartoon.route){
        composable(
            route = EpisodeBottom.Comment.route
        ){
            CommentPage(navController,epId,cartoonid)
        }
        composable(
            route = EpisodeBottom.Next.route
        ){

        }
        composable(
            route = EpisodeBottom.Back.route
        ){

        }
        composable(
            route = EpisodeBottom.Cartoon.route
        ){
            CartoonThisChapter(navController,epId)
        }
    }
}

@SuppressLint("RestrictedApi")
@Composable
fun EpisodeBottomBar(navController: NavHostController){
    val navigationItems = listOf(
        EpisodeBottom.Comment,
        EpisodeBottom.Back,
        EpisodeBottom.Next
    )
    NavigationBar (
        modifier = Modifier
            .height(60.dp),
            containerColor = Color(184,0,0),
        contentColor = Color.Transparent
    ){
        navigationItems.forEachIndexed{index, screen ->
            NavigationBarItem(
                icon = { Icon(imageVector = screen.icon,contentDescription = null, tint = Color.White) },
                selected = (false),
                onClick = {
                    navController.navigate(screen.route)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EpisodeTopBar(){
    val cartoonname = "ep 1"
    TopAppBar(
        title = { Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = cartoonname,
                letterSpacing = 1.75.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            ) }
                },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors
            (containerColor = Color(184,0,0)))
}



