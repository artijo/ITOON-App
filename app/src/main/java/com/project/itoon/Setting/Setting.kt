package com.project.itoon.Setting

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
import com.project.itoon.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun Settingpage(navController:NavHostController){
    val navHostController = rememberNavController()
    val contextForToast = LocalContext.current.applicationContext
    val createClient = API.create()
    val data = navController.previousBackStackEntry?.savedStateHandle?.get<User>("data")?:
    User(0,"","","","")
    val userId by remember{ mutableStateOf(data.id) }
    val initialuser = User(0,"","","","")
    var userItems by remember { mutableStateOf(initialuser) }
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    LaunchedEffect(lifecycleState){
        when(lifecycleState){
            Lifecycle.State.DESTROYED ->{}
            Lifecycle.State.INITIALIZED ->{}
            Lifecycle.State.CREATED ->{}
            Lifecycle.State.STARTED ->{}
            Lifecycle.State.RESUMED ->{
                createClient.getUSerbyID(userId.toString())
                    .enqueue(object: Callback<User> {
                        override fun onResponse(call: Call<User>,
                                                response: Response<User>
                        ){
                            if(response.isSuccessful){
                                userItems= User(
                                    response.body()!!.id,
                                    response.body()!!.email,
                                    response.body()!!.name,
                                    response.body()!!.password,
                                    response.body()!!.phone
                                )
                            }else{
                                Toast.makeText(contextForToast,"User ID Not Found", Toast.LENGTH_LONG).show()
                            }
                        }
                        override fun onFailure(call: Call<User>,t: Throwable){
                            Toast.makeText(contextForToast, "Error onFailure" + t.message,Toast.LENGTH_LONG).show()
                        }
                    })

            }
        }

    }
    Column(
        modifier = Modifier.fillMaxHeight()
        , horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.padding(5.dp))
        Column (
            modifier = Modifier.padding(10.dp)
        ){
            Text(text = "บัญชีผู้ใช้",
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Row (
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                ){
                Row {
                    Box (
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = Color.White,
                                shape = RoundedCornerShape(100.dp)
                            )
                            .size(50.dp)
                    ){
                        Image(painter = painterResource(R.drawable.logo),
                            contentDescription = "testprofilepic",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = Color.White,
                                    shape = RoundedCornerShape(100.dp),
                                )
                                .clip(CircleShape)

                        )

                    }
                    Text(
                        text = "${data.name} Hello",
                        fontSize = 15.sp,
                        modifier = Modifier.padding(top = 10.dp,start = 5.dp)
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                }
                IconButton(onClick = { /*TODO*/ },
                    modifier = Modifier.padding(end = 5.dp) ) {
                    Icon(imageVector = Icons.Default.Edit,
                        contentDescription = "edit",
                        tint =Color.Black
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
                    text = "${data.email} Hi",
                    modifier = Modifier.padding(start = 5.dp, top = 5.dp))
            }
            Spacer(modifier = Modifier.padding(5.dp))
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

