package com.project.itoon.NavBottomBar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.project.itoon.R

@Composable
fun FavoritePage(navHostController: NavHostController){
    val contextForToast = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Image(painter = painterResource(id = R.drawable.favorite), contentDescription = null,
            modifier = Modifier
                .padding(10.dp)
                .size(200.dp)
                .clip(shape = RoundedCornerShape(75.dp)))
        Text(text = "หน้าสำหรับคุณ")

    }
}