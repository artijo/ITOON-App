package com.project.itoon.NavBottomBar

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.project.itoon.API
import com.project.itoon.LoginAndSignUp.Creator
import com.project.itoon.Setting.SharedPreferencesManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun creatorREG(navController: NavHostController){
    val context = LocalContext.current.applicationContext
    lateinit var sharedPreferences: SharedPreferencesManager
    sharedPreferences = SharedPreferencesManager(context)
    val userId = sharedPreferences.userId
    var creator by remember { mutableStateOf(Creator(0,0,"")) }
    val createAPI = API.create()
    var regConfirm by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true){
        createAPI.getCreator(userId).enqueue(object : Callback<Creator> {
            override fun onResponse(call: Call<Creator>, response: Response<Creator>) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        creator = response.body()!!
                    }
                }
            }

            override fun onFailure(call: Call<Creator>, t: Throwable) {
                Log.d("Creator", "onFailure: ${t.message}")
            }
        })
    }
Column(modifier = Modifier
    .fillMaxHeight()
    .padding(10.dp),
    horizontalAlignment = Alignment.CenterHorizontally) {
    Text(text = "สมัครเป็น Creator", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(CenterHorizontally))
    if(creator.userId != 0 && creator.status == "verified"){
        Text(text = "คุณเป็น Creator แล้ว", fontSize = 16.sp, modifier = Modifier.align(CenterHorizontally))
    }else if (creator.userId != 0 && creator.status == "pending") {
        Text(
            text = "คุณสมัครเป็น Creator แล้ว รอการตรวจสอบ",
            fontSize = 16.sp,
            modifier = Modifier.align(CenterHorizontally)
        )
        Text(text = "สถานะตอนนี้ : ${creator.status}", fontSize = 16.sp, modifier = Modifier.align(CenterHorizontally))
    }else {
        Text(text = "สมัครเป็น Creator และเริ่มสร้างการ์ตูนของคุณได้ทันที", fontSize = 16.sp, modifier = Modifier.align(CenterHorizontally))
        Button(onClick = {
            regConfirm = true
        }) {
            Text(text = "สมัครเป็น Creator")
        }
        if (regConfirm) {
            AlertDialog(onDismissRequest = { regConfirm = false },
                title= { Text(text = "สมัครเป็น Creator")},
                text = { Text(text = "คุณต้องการสมัครเป็น Cretor ใช่ไหม?")},
                confirmButton = {
                    TextButton(onClick = {
                       regConfirm=false
                        registerCreator(userId)
                        navController.navigateUp()

                    }) {
                        Text(text = "ใช่")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        regConfirm=false
                    }) {
                        Text(text = "ไม่")
                    }
                },

                )
        }
    }

}
}

fun registerCreator(userid:Int){
    val createAPI = API.create()
    createAPI.regCreator(userid).enqueue(object : Callback<Creator> {
        override fun onResponse(call: Call<Creator>, response: Response<Creator>) {
            if(response.isSuccessful){
                val creator = response.body()
                if(creator != null){
                    Log.d("Creator", "Creator: ${creator}")
                }
            }
        }

        override fun onFailure(call: Call<Creator>, t: Throwable) {
            Log.d("Creator", "onFailure: ${t.message}")
        }
    })
}
