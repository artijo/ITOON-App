package com.project.itoon.CartoonNav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Backspace
import androidx.compose.ui.graphics.vector.ImageVector

sealed class CartoonBottom(val route:String,val name:String,val icon:ImageVector) {
    object Cartoon:CartoonBottom(route = "cartoon_page", name = "cartoon", Icons.AutoMirrored.Filled.Backspace)
}