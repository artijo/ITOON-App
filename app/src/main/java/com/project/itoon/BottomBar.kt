package com.project.itoon

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBar(val route:String,val name:String,val icon: ImageVector){
    object Home: BottomBar(route = "home_screen", name = "Home", icon = Icons.Default.Home)
    object Profile: BottomBar(route = "profile_screen", name = "Profile", icon = Icons.Default.Person)
    object Favorite: BottomBar(route = "favorite_screen", name = "Favorite", icon = Icons.Default.Favorite)
}