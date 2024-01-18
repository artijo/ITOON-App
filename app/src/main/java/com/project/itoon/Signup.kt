package com.project.itoon

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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Signup(name:String,onNamechage:(String)->Unit,email:String,onEmailchage:(String)->Unit,
           password:String,onPasswordchage:(String)->Unit,confirmpass:String,onConfirmchage:(String)->Unit){
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(20.dp)

    ) {
        Spacer(modifier = Modifier.padding(8.dp))
        Text(text = "สมัครสมาชิก", fontSize = 25.sp,fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.padding(8.dp))
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
            , horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Center
        ){
            Text(text = "อีเมล",fontSize = 16.sp,modifier = Modifier.padding(bottom = 5.dp, top = 5.dp))
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
            Text(text = "ชื่อ",fontSize = 16.sp,modifier = Modifier.padding(bottom = 5.dp, top = 5.dp))
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
            Text(text = "รหัสผ่าน",fontSize = 16.sp,modifier = Modifier.padding(bottom = 5.dp, top = 5.dp))
            BasicTextField(value = password,
                onValueChange = onPasswordchage,
                modifier = Modifier
                    .width(400.dp)
                    .border(
                        border = BorderStroke(1.dp, color = Color.Red),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(20.dp)
            )
            Text(text = "ยืนยันรหัสผ่าน",fontSize = 16.sp,modifier = Modifier.padding(bottom = 5.dp, top = 5.dp))
            BasicTextField(value = confirmpass,
                onValueChange = onConfirmchage,
                modifier = Modifier
                    .width(400.dp)
                    .border(
                        border = BorderStroke(1.dp, color = Color.Red),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(20.dp)
            )

        }
        Spacer(modifier = Modifier.padding(8.dp))
        TextButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .width(129.dp)
                .background(color = Color(184, 0, 0), shape = RoundedCornerShape(20.dp)),

            ) {
            Text(text = "สมัครสมาชิก",color = Color.White) }
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