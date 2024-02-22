package com.project.itoon.TopLazyRow

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.project.itoon.Setting.SharedPreferencesManager
import com.project.itoon.firstpageapi.CartoonAPI

@Composable
fun Like(navController:NavHostController){
    val creatClient = CartoonAPI.create()
    val contextForToast = LocalContext.current.applicationContext
    lateinit var sharedPreferenceManager : SharedPreferencesManager
    sharedPreferenceManager = SharedPreferencesManager(context = contextForToast)
    val userId by remember{ mutableStateOf(sharedPreferenceManager.userId) }
}