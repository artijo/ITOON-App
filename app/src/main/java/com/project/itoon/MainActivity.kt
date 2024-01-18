package com.project.itoon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.project.itoon.ui.theme.ITOONTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ITOONTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TestScreen()
                }
            }
        }
    }
}

@Composable
fun LoginPage(email:String,onEmailChange:(String) -> Unit,
              password:String,onPassChange:(String) -> Unit)
{
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Image(painter = painterResource(id = R.drawable.logo), contentDescription = null ,modifier = Modifier.size(250.dp), contentScale = ContentScale.Fit)
        Text(text = "เข้าสู่ระบบ",fontSize = 25.sp, fontWeight = FontWeight.Bold)
        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            , horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Center) {
            Text(text = "อีเมล",fontSize = 16.sp,modifier = Modifier.padding(bottom = 5.dp))
            BasicTextField(value = email , onValueChange = onEmailChange ,modifier = Modifier
                .width(400.dp)
                .border(
                    border = BorderStroke(1.dp, color = Color.Red),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(20.dp)
            )
            Text(text = "รหัสผ่าน",fontSize = 16.sp,modifier = Modifier.padding(bottom = 5.dp))
            BasicTextField(value = password , onValueChange = onPassChange ,modifier = Modifier
                .width(400.dp)
                .border(
                    border = BorderStroke(1.dp, color = Color.Red),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(20.dp),
                visualTransformation = PasswordVisualTransformation()
            )
        }
        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            , horizontalAlignment = Alignment.End){
            TextButton(onClick = {}) {
                Text(text = "ลืมรหัสผ่าน",color = Color.Black,modifier = Modifier.alpha(0.5f))
            }
        }
        TextButton(onClick = { /*TODO*/ },modifier = Modifier
            .width(129.dp)
            .background(color = Color(184, 0, 0), shape = RoundedCornerShape(20.dp))) {
            Text(text = "เข้าสู่ระบบ",color = Color.White)
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Text(text = "หรือ",modifier =Modifier.alpha(0.5f))
        TextButton(onClick = { /*TODO*/ },modifier = Modifier
            .width(129.dp)
        ) {
            Text(text = "สมัครสมาชิก",color = Color.Black)
        }

    }
}

@Composable
fun TestScreen(){
    var email by rememberSaveable{ mutableStateOf("") }
    var password by rememberSaveable{ mutableStateOf("") }

    LoginPage(email = email, onEmailChange = { email = it }, password = password, onPassChange = {password})
}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello I am here For your$name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ITOONTheme {
        Greeting(name = "Tharit")
    }
}