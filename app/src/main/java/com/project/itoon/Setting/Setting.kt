package com.project.itoon.Setting

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.project.itoon.API
import com.project.itoon.LoginAndSignUp.User
import com.project.itoon.LoginAndSignUp.startLoginActivity
import com.project.itoon.NavBottomBar.BottomBar
import com.project.itoon.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@SuppressLint("SuspiciousIndentation")
@Composable
fun Settingpage(navController:NavHostController){
    val contextForToast = LocalContext.current.applicationContext
    val createClient = API.create()
    val data = navController.previousBackStackEntry?.savedStateHandle?.get<User>("data")?:
    User(0,"","","","")
    val initialuser = User(0,"","","","")
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
                                label = { Text(text = "โปรดใส่อีเมลใหม่ของคุณ") }
                            )
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            showDialog=false
                            createClient.updateProfile(userId.toString(),textFieldEmail,textFieldName)
                                .enqueue(
                                    object :Callback<Profile>{
                                        override fun onResponse(
                                            call: Call<Profile>,
                                            response: Response<Profile>
                                        ) {
                                            if(response.isSuccessful){
                                                Toast.makeText(contextForToast,
                                                    "Successfully Updated",
                                                    Toast.LENGTH_LONG).show()
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
            Text(text = "การแจ้งเตือน",
                fontSize = 15.sp,
                modifier = Modifier.padding(top = 5.dp))
            Row (
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ){
                Row {
                    Icon(
                        imageVector = Icons.Default.NotificationsOff ,
                        contentDescription = "notification",
                        modifier = Modifier.padding(top=5.dp)
                    )
                    Text(text = "การแจ้งเตือนเปิดอยู่หากต้องการปิด\nไปที่ตั้งค่าของอุปกรณ์ของคุณ",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top=2.dp)
                    )
                }
                Button(onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(Color.Black)
                ) {
                    Text(
                        text = "ตั้งค่าอุปกรณ์" ,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp)
                }
            }

        }
    }
}

