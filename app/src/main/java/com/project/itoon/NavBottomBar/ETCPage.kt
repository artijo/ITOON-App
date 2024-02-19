package com.project.itoon.NavBottomBar

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.itoon.R
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

import com.project.itoon.Setting.SettingClass
import com.project.itoon.Setting.Settingpage


@Composable
fun ETCPage(navHostController: NavHostController){
    val contextForToast = LocalContext.current
    val ctx = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
//            .border(1.dp, Color.Blue)
    ) {
        Row (modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.Start)
            .background(Color(211, 211, 211, 50))
            , horizontalArrangement = Arrangement.SpaceBetween
        ){
            Column(
                Modifier
                    .padding(10.dp)
            ) {
                Row {
                    Icon(painter = painterResource(id = R.drawable.coin), contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .border(
                                5.dp, Color(255, 215, 0),
                                RoundedCornerShape(100.dp)
                            ), tint = Color(218,153,43,100)
                    )
                    Text(text = "200", fontSize = 25.sp, modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 5.dp), textDecoration = TextDecoration.Underline)
                }

            }

            Column(
                Modifier
//                    .border(1.dp, Color.Cyan)
                    .padding(start = 10.dp, end = 15.dp, top = 10.dp, bottom = 10.dp)

            ) {
                Button(onClick = {
                    val urlIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://10.52.201.0:5173/coin-transaction")
                    )
                    ctx.startActivity(urlIntent)
                                 },colors=ButtonDefaults.buttonColors(Color(184,0,0)),
                    shape = RoundedCornerShape(15,15,15,15)
                ) {
                    Text(text = "เติมเหรียญ", fontSize = 15.sp)
                }
            }


        }
        Divider(color = Color.LightGray, thickness = 1.dp)
        Row (
            Modifier
//                .border(1.dp, Color.Blue)
                .fillMaxWidth()
            , horizontalArrangement = Arrangement.SpaceAround,

        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .border(1.dp, Color(0, 0, 0, 25))
                    .weight(1f, true)
                    .padding(top = 20.dp)
                    .clickable {
                        navHostController.navigate(BottomBar.SearchPage.route)
                    }
            ) {

                Icon(imageVector = Icons.Default.Search, contentDescription = null,Modifier.size(25.dp))
                Text(text = "ค้นหา")
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .border(1.dp, Color(0, 0, 0, 25))
                    .weight(1f, true)
                    .padding(top = 20.dp)
                    .clickable { navHostController.navigate(SettingClass.Setting.route) }
            ) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = null,Modifier.size(25.dp))
                Text(text = "ตั้งค่า")
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .border(1.dp, Color(0, 0, 0, 25))
                    .weight(1f, true)
                    .padding(top = 20.dp)
                    .clickable {
                        val urlIntent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("http://10.52.201.0:5173/login")
                        )
                        ctx.startActivity(urlIntent)
                    }
            ) {
                Icon(painter = painterResource(id = R.drawable.edit), contentDescription = null,Modifier.size(25.dp))
                Text(text = "วาดการ์ตูน")
            }
            
            
        }
    }
}