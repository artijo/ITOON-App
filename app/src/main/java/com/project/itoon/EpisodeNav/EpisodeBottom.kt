package com.project.itoon.EpisodeNav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Backspace
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material.icons.filled.ArrowCircleLeft
import androidx.compose.material.icons.filled.ArrowCircleRight
import androidx.compose.ui.graphics.vector.ImageVector

sealed class EpisodeBottom(val route:String , val name:String , val icon:ImageVector) {
    object Comment:EpisodeBottom(route = "comment_screen", name = "Comment", icon = Icons.AutoMirrored.Filled.Comment)
    object Next:EpisodeBottom(route = "next_screen", name = "Next", icon = Icons.Default.ArrowCircleRight)
    object Back:EpisodeBottom(route = "back_screen", name = "Back", icon = Icons.Default.ArrowCircleLeft)
    object Cartoon:EpisodeBottom(route = "cartoon_page", name = "cartoon", Icons.AutoMirrored.Filled.Backspace)
}