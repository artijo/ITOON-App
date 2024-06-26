package com.project.itoon.Setting

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_SHOW_APP_INFO
import android.os.Bundle
import android.provider.Settings.ACTION_WIFI_SETTINGS
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getContextForLanguage
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import com.project.itoon.API
import com.project.itoon.EpisodeNav.EpisodeBottom
import com.project.itoon.LoginAndSignUp.LoginActivity
import com.project.itoon.LoginAndSignUp.LoginAndSignUp
import com.project.itoon.LoginAndSignUp.User
import com.project.itoon.LoginAndSignUp.startLoginActivity
import com.project.itoon.MainActivity
import com.project.itoon.NavBottomBar.BottomBar
import com.project.itoon.ui.theme.ITOONTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



@SuppressLint("RestrictedApi")
@Composable
fun Settingpage(navController:NavHostController){
    val contextForToast = LocalContext.current.applicationContext
    val createClient = API.create()
    val data = navController.previousBackStackEntry?.savedStateHandle?.get<User>("data")?:
    User(0,"","","","",0)
    val initialuser = User(0,"","","","",0)
    var userItems by remember { mutableStateOf(initialuser) }
    var updateprofile = remember { mutableStateListOf<User>() }
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState() 
    var showDialog by remember { mutableStateOf(false) }
    lateinit var sharedPreferenceManager : SharedPreferencesManager
    sharedPreferenceManager = SharedPreferencesManager(context = contextForToast)
    val userId = sharedPreferenceManager.userId
    var textFieldEmail by remember { mutableStateOf(data.email)  }
    var textFieldName by remember { mutableStateOf(data.name)  }

    LaunchedEffect(lifecycleState){
        when(lifecycleState){
            Lifecycle.State.DESTROYED ->{}
            Lifecycle.State.INITIALIZED ->{}
            Lifecycle.State.CREATED ->{}
            Lifecycle.State.STARTED ->{}
            Lifecycle.State.RESUMED ->{

            }
        }
    }
    Column(
        modifier = Modifier.fillMaxHeight()
        , horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column (
            modifier = Modifier.padding(10.dp)
        ){
            if(!sharedPreferenceManager.isLoggedIn){
                Row (
                    Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clickable {
                            startLoginActivity(contextForToast)
                        }
                        .background(Color(184, 0, 0)),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically)
                {
                    Text(text = "Login",color = Color.White,fontSize = 20.sp)
                }
            }else{
                Spacer(modifier = Modifier.padding(5.dp))
            Text(text = "บัญชีผู้ใช้",
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Row (
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                ){
                Row {
                    Text(
                        text = data.name,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(top = 10.dp,start = 5.dp)
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                }
                IconButton(onClick = {
                    showDialog=true
                },
                    modifier = Modifier.padding(end = 5.dp) ) {
                    Icon(imageVector = Icons.Default.Edit,
                        contentDescription = "edit",
                        tint =Color.Black
                    )
                }
                if(showDialog){
                    AlertDialog(
                        onDismissRequest = {showDialog=false},
                        title = { Text(text ="เปลี่ยนชื่อขอคุณ")},
                        text = {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally)
                            {
                                OutlinedTextField(
                                    value = textFieldName,
                                    onValueChange = { textFieldName = it },
                                    label = { Text(text = "โปรดใส่ชื่อใหม่ของคุณ") }
                                )

                                OutlinedTextField(
                                    value = textFieldEmail,
                                    onValueChange = { textFieldEmail = it },
                                    label = { Text(text = "โปรดใส่อีเมลใหม่ของคุณ") },
                                    enabled = false
                                )
                            }
                        },
                        confirmButton = {
                            TextButton(onClick = {

                                createClient.updateProfile(userId.toString(),textFieldEmail,textFieldName)
                                    .enqueue(
                                        object :Callback<Profile>{
                                            @SuppressLint("RestrictedApi")
                                            override fun onResponse(
                                                call: Call<Profile>,
                                                response: Response<Profile>
                                            ) {
                                                if(response.isSuccessful){
                                                    Toast.makeText(contextForToast,
                                                        "Successfully Updated",
                                                        Toast.LENGTH_LONG).show()
                                                    showDialog=false
                                                }
                                                else{
                                                    Toast.makeText(contextForToast,
                                                        "Update Failure",
                                                        Toast.LENGTH_LONG).show()
                                                }
                                            }

                                            override fun onFailure(call: Call<Profile>, t: Throwable) {
                                                Toast.makeText(contextForToast,
                                                    "Error on Failure"+t.message,
                                                    Toast.LENGTH_LONG).show()
                                            }
                                        }
                                    )
                                textFieldName=""
                                textFieldEmail=""
                                if (navController.currentBackStack.value.size >= 2){
                                    navController.popBackStack()
                                }
                                navController.navigate(BottomBar.ETC.route)
                            }) {
                                Text(text = "Save")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = {
                                showDialog=false
                                textFieldName=""
                                textFieldEmail=""
                            }) {
                                Text(text = "Cancle")
                            }
                        }
                    )

                }
            }

            Spacer(modifier = Modifier.padding(5.dp))
            Divider(color = Color.LightGray, thickness = 1.dp)
            Text(text = "อีเมล",
                fontSize = 15.sp,
                modifier = Modifier.padding(top = 5.dp)
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = data.email,
                    modifier = Modifier.padding(start = 5.dp, top = 5.dp))
            }
            Spacer(modifier = Modifier.padding(5.dp))
                }
            Divider(color = Color.LightGray, thickness = 1.dp)
        }
    }
}


