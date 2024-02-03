package com.project.itoon

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun SignUpContent(name:String,onNamechage:(String)->Unit,email:String,onEmailchage:(String)->Unit,
                  password:String,onPasswordchage:(String)->Unit,confirmpass:String,onConfirmchage:(String)->Unit,
                  phone:String,onPhonechange:(String)->Unit){
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
        , horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Center
    ){
        Text(text = "*อีเมล",fontSize = 16.sp,modifier = Modifier.padding(bottom = 5.dp, top = 5.dp))
        BasicTextField(value = email,
            onValueChange = onEmailchage,
            modifier = Modifier
                .width(400.dp)
                .border(
                    border = BorderStroke(1.dp, color = Color.Red),
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(20.dp)
        )
        Text(text = "*ชื่อ",fontSize = 16.sp,modifier = Modifier.padding(bottom = 5.dp, top = 5.dp))
        BasicTextField(value = name,
            onValueChange = onNamechage,
            modifier = Modifier
                .width(400.dp)
                .border(
                    border = BorderStroke(1.dp, color = Color.Red),
                    shape = RoundedCornerShape(10.dp)

                )
                .padding(20.dp)
        )
        Text(text = "*รหัสผ่าน",fontSize = 16.sp,modifier = Modifier.padding(bottom = 5.dp, top = 5.dp))
        BasicTextField(value = password,
            onValueChange = onPasswordchage,
            modifier = Modifier
                .width(400.dp)
                .border(
                    border = BorderStroke(1.dp, color = Color.Red),
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(20.dp),
            visualTransformation = PasswordVisualTransformation()
        )
        Text(text = "*ยืนยันรหัสผ่าน",fontSize = 16.sp,modifier = Modifier.padding(bottom = 5.dp, top = 5.dp))
        BasicTextField(value = confirmpass,
            onValueChange = onConfirmchage,
            modifier = Modifier
                .width(400.dp)
                .border(
                    border = BorderStroke(1.dp, color = Color.Red),
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(20.dp),
            visualTransformation = PasswordVisualTransformation()
        )
        Text(text = "*เบอร์โทรศัพท์",fontSize = 16.sp,modifier = Modifier.padding(bottom = 5.dp,top = 5.dp))
        BasicTextField(value = phone,
            onValueChange = onPhonechange,
            modifier = Modifier
                .width(400.dp)
                .border(
                    border = BorderStroke(1.dp, color = Color.Red),
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(20.dp),
        )
    }
}

@Composable
fun Signup(navHostController: NavHostController){
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmpass by rememberSaveable{ mutableStateOf("") }
    var phone by remember{ mutableStateOf("") }
    var context  = LocalContext.current.applicationContext

    val createUser = API.create()

    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(20.dp)

    ) {
        Spacer(modifier = Modifier.padding(8.dp))
        Text(text = "สมัครสมาชิก", fontSize = 25.sp,fontWeight = FontWeight.Bold)
        SignUpContent(
            name = name,
            onNamechage = { name = it },
            email = email,
            onEmailchage = { email = it },
            password = password,
            onPasswordchage = { password = it },
            confirmpass = confirmpass,
            onConfirmchage = {confirmpass = it},
            phone = phone,
            onPhonechange = {phone = it}
        )


        Spacer(modifier = Modifier.padding(8.dp))

        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = {
                if(password != confirmpass){
                    Toast.makeText(context,"The Password isn't match",Toast.LENGTH_LONG).show()
                    return@Button
                }
                if(email.isBlank() || name.isBlank() || confirmpass.isBlank() || password.isBlank() || phone.isBlank()) {
                    when {
                        email.isBlank() -> Toast.makeText(
                            context,
                            "Please enter your email.",
                            Toast.LENGTH_LONG
                        ).show()

                        name.isBlank() -> Toast.makeText(
                            context,
                            "Please enter your name.",
                            Toast.LENGTH_LONG
                        ).show()

                        password.isBlank() -> Toast.makeText(
                            context,
                            "Please enter your password.",
                            Toast.LENGTH_LONG
                        ).show()

                        confirmpass.isBlank() -> Toast.makeText(
                            context,
                            "Please confirm your password.",
                            Toast.LENGTH_LONG
                        ).show()

                        phone.isBlank() -> Toast.makeText(
                            context,
                            "Please enter your phone number.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    return@Button
                }else{
                      createUser.insertUser(email,name,password,phone)
                          .enqueue(object : Callback<User>{
                              override fun onResponse(call: Call<User>, response: Response<User>) {
                                  if(response.isSuccessful){
                                      Toast.makeText(context,"Sign Successfully",Toast.LENGTH_LONG).show()
                                      email = ""
                                      name = ""
                                      password = ""
                                      phone = ""
                                      navHostController.navigateUp()
                                  }else {
                                      Toast.makeText(context, "Fail to SignUp", Toast.LENGTH_LONG)
                                          .show()
                                  }
                              }
                              override fun onFailure(call: Call<User>, t: Throwable) {
                                  Toast.makeText(context,"ระบบมีปัญหาในการใช้งาน",Toast.LENGTH_LONG).show()
                              }
                          })
            }},colors = ButtonDefaults.buttonColors(Color(184,0,0)),
            modifier = Modifier
                .width(129.dp), shape = RoundedCornerShape(10.dp)
            ) {
            Text(text = "สมัครสมาชิก",color = Color.White)
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Text(text = "หรือ",modifier =Modifier.alpha(0.5f))

        TextButton(onClick = { /*TODO*/ },
            modifier = Modifier
                .width(129.dp)
        ) {
            Text(text = "เข้าสู่ระบบ",color = Color.Black)
        }

    }
}