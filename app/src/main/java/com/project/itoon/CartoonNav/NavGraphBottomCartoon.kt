package com.project.itoon.CartoonNav

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.project.itoon.cartoonPage.CartoonAllEp

@Composable
fun NavGraphCartoon(navHostController: NavHostController,cartoonId:Int,cartoonName:String){
    NavHost(navController = navHostController, startDestination = CartoonBottom.Cartoon.route){
        composable(
            route = CartoonBottom.Cartoon.route
        ){
            CartoonAllEp(navController = navHostController)
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartoonTopApp(cartoonName: String){
    TopAppBar(title = { Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = cartoonName,letterSpacing = 1.75.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White)
    } },colors = TopAppBarDefaults.centerAlignedTopAppBarColors
        (containerColor = Color(184,0,0)))
}