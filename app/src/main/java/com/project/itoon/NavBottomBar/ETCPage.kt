package com.project.itoon.NavBottomBar

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import com.project.itoon.API
import com.project.itoon.LoginAndSignUp.User
import com.project.itoon.R
import com.project.itoon.Setting.SettingClass
import com.project.itoon.Setting.SharedPreferencesManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun ETCPage(navtController: NavHostController){
    val contextForToast = LocalContext.current
    val ctx = LocalContext.current
    lateinit var sharedPreferenceManager : SharedPreferencesManager
    sharedPreferenceManager = SharedPreferencesManager(contextForToast)
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()
    val createClient = API.create()
    val userId by remember {
        mutableStateOf(sharedPreferenceManager.userId)
    }
    val initialuser = User(0,"","","","",0)
    var userItems by remember { mutableStateOf(initialuser) }
    LaunchedEffect(lifecycleState){
        when(lifecycleState){
            Lifecycle.State.DESTROYED ->{}
            Lifecycle.State.INITIALIZED ->{}
            Lifecycle.State.CREATED ->{}
            Lifecycle.State.STARTED ->{}
            Lifecycle.State.RESUMED ->{
                createClient
                    .getUSerbyID(userId)
                    .enqueue(object : Callback<User> {
                        override fun onResponse(
                            call: Call<User>,
                            response: Response<User>
                        ) {
                            if (response.isSuccessful) {
                                if (sharedPreferenceManager.isLoggedIn)
                                    userItems = User(
                                        response.body()!!.id,
                                        response.body()!!.email,
                                        response.body()!!.name,
                                        response.body()!!.password,
                                        response.body()!!.phone?:"",
                                        response.body()!!.coin
                                    )
                            } else {

                            }
                        }

                        override fun onFailure(call: Call<User>, t: Throwable) {
                            Toast
                                .makeText(
                                    contextForToast, "Error onFailure" + t.message,
                                    Toast.LENGTH_LONG
                                )
                                .show()
                        }
                    })

            }
        }
    }
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
                    Text(text = "${userItems.coin}", fontSize = 25.sp, modifier = Modifier
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
                        navtController.navigate(BottomBar.SearchPage.route)
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
                    .clickable {
                        navtController.currentBackStackEntry?.savedStateHandle?.set("data",
                            User(userItems.id,userItems.email,userItems.name,userItems.password,userItems.phone,userItems.coin))
                        navtController.navigate(SettingClass.Setting.route)
                    }
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

@Composable
fun getdata(userItems: User, context: Context){
    val createClient = API.create()
    lateinit var sharedPreferenceManager:SharedPreferencesManager
    sharedPreferenceManager = SharedPreferencesManager(context)
    val userItems by remember { mutableStateOf(userItems) }
    val userId by remember{ mutableStateOf(sharedPreferenceManager.userId) }
    createClient
        .getUSerbyID(userId)
        .enqueue(object : Callback<User> {
            override fun onResponse(
                call: Call<User>,
                response: Response<User>
            ) {
                if (response.isSuccessful) {
                    if (sharedPreferenceManager.isLoggedIn)
                        userItems.name
                        userItems.email
                } else {

                }
            }
            override fun onFailure(call: Call<User>, t: Throwable) {

            }
        })

}