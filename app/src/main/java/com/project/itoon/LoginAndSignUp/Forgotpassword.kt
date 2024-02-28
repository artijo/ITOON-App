package com.project.itoon.LoginAndSignUp

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.project.itoon.API
import com.project.itoon.firstpageapi.Forgotpass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ForgotPassword(navHostController: NavHostController){
    var password by rememberSaveable {
        mutableStateOf("")
    }
    var confirmpassword by rememberSaveable {
        mutableStateOf("")
    }
    var email by rememberSaveable {
        mutableStateOf("")
    }
    val  creatClient = API.create()
    val contextForToast = LocalContext.current.applicationContext
    var itemList = remember {
        mutableStateListOf<Forgotpass>()
    }
    var allEmailList = remember {
        mutableStateListOf<AllEmail>()
    }


    LaunchedEffect(true) {
        itemList.clear()
        allEmailList.clear()
        creatClient.allemail()
            .enqueue(object :Callback<List<AllEmail>>{
                override fun onResponse(
                    call: Call<List<AllEmail>>,
                    response: Response<List<AllEmail>>
                ) {
                    if(response.isSuccessful){
                            Log.i("dataAllmailNow","----------------------------------------------")
//                            Log.i("dataAllmailNow",response.body().toString())
                            response.body()?.forEach {
                                allEmailList.add(AllEmail(it.email))
                            }
                    }
                }

                override fun onFailure(call: Call<List<AllEmail>>, t: Throwable) {
                    Log.i("dataAllmailNowErr","Error na ka")
                }
            })
    }

//    allEmailList.forEach {
//        Log.i("dataAllmailNow",it.email)
//    }
//    Log.i("dataAllmailNow",allEmailList.toList().toString())

    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "ลืมรหัสผ่าน", fontSize = 25.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.padding(30.dp))
        Row (
            Modifier
                .width(350.dp)
                .height(70.dp)){
            Column {
                Text(text = "อี-เมล:")
                BasicTextField(value = email,
                    onValueChange = {email = it},
                    Modifier
                        .fillMaxSize()
                        .border(
                            border = BorderStroke(1.dp, color = Color.Red),
                            shape = RoundedCornerShape(10.dp)

                        ).padding(15.dp),

                )
            }

        }
        Spacer(modifier = Modifier.padding(10.dp))
        Row(
            Modifier
                .width(350.dp)
                .height(70.dp)) {
            Column {
                Text(text = "กรอกรหัสผ่านใหม่:")
               BasicTextField(value = password,
                    onValueChange = {password = it},
                   Modifier
                       .fillMaxSize()
                       .border(
                           border = BorderStroke(1.dp, color = Color.Red),
                           shape = RoundedCornerShape(10.dp)

                       ).padding(15.dp),
                   visualTransformation = PasswordVisualTransformation()

               )
            }

        }
        Spacer(modifier = Modifier.padding(10.dp))
        Row(
            Modifier
                .width(350.dp)
                .height(70.dp)) {
            Column {
                Text(text = "ยืนยันรหัสผ่าน:")
                BasicTextField(value = confirmpassword,
                    onValueChange = {confirmpassword = it},
                    Modifier
                        .fillMaxSize()
                        .border(
                            border = BorderStroke(1.dp, color = Color.Red),
                            shape = RoundedCornerShape(10.dp)

                        ).padding(15.dp),
                    visualTransformation = PasswordVisualTransformation()

                )
            }

        }
        Spacer(modifier = Modifier.padding(30.dp))
        Row {
            Button(onClick = {
                var check = false
                allEmailList.forEach {
                    Log.i("emailnow",email)
                    Log.i("emailnowcheck",it.email)
                    if (email==it.email){
                        check=true
                    }
                }
                if (check){
                    if (password==confirmpassword){
                        creatClient.forgotpassword(email, password)
                            .enqueue(object :Callback<Forgotpass>{
                                override fun onResponse(call: Call<Forgotpass>, response: Response<Forgotpass>) {
                                    itemList.add(Forgotpass(response.body()?.email,response.body()?.password))
                                    Toast.makeText(contextForToast,"เปลี่ยนรหัสผ่านเรียบร้อย",Toast.LENGTH_LONG).show()
                                }

                                override fun onFailure(call: Call<Forgotpass>, t: Throwable) {
                                    Toast.makeText(contextForToast,"onfail", Toast.LENGTH_LONG).show()
                                }
                            })
                        navHostController.navigateUp()
                    }else{
                        Toast.makeText(contextForToast,"Password ไม่ตรงกัน", Toast.LENGTH_LONG).show()
                    }
                }else{
                    Toast.makeText(contextForToast,"ไม่พบอีเมลผู้ใช้", Toast.LENGTH_LONG).show()
                }

            }, colors = ButtonDefaults.buttonColors(Color(184,0,0))

            ) {
                Text(text = "Submit", color = Color.White)
            }
        }

    }
}