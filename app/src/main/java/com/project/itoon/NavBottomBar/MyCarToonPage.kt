package com.project.itoon.NavBottomBar

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.NavHostController
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Lifecycle
import androidx.navigation.compose.rememberNavController
import com.project.itoon.LoginAndSignUp.LoginActivity
import com.project.itoon.MenuLazyrow
import com.project.itoon.Setting.SharedPreferencesManager
import com.project.itoon.TopLazyRow.NavGraphTopApp

@SuppressLint("RestrictedApi")
@Composable
fun MyCarToonPage(LazynavHostController: NavHostController){
    val contextForToast = LocalContext.current.applicationContext
    var navHostController = rememberNavController()

    lateinit var sharedPreferences:SharedPreferencesManager
    sharedPreferences = SharedPreferencesManager(contextForToast)

    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    LaunchedEffect(lifecycleState){
        when(lifecycleState){
            Lifecycle.State.DESTROYED -> {}
            Lifecycle.State.INITIALIZED -> {}
            Lifecycle.State.CREATED -> {}
            Lifecycle.State.STARTED -> {}
            Lifecycle.State.RESUMED -> {

            }
        }
    }

    Scaffold(
        topBar = { MenuLazyrow(navHostController) }
    ) {
        paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            NavGraphTopApp(navHostController)
        }
    }
}