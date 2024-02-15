package com.project.itoon.LoginAndSignUp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.project.itoon.API
import com.project.itoon.MainActivity
import com.project.itoon.NavBottomBar.BottomBar
import com.project.itoon.R
import com.project.itoon.Setting.SharedPreferencesManager
import com.project.itoon.ui.theme.ITOONTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var context = LocalContext.current.applicationContext
            lateinit var share : SharedPreferencesManager
            share = SharedPreferencesManager(context)
            ITOONTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    if(share.isLoggedIn){
                        finish()
                    }else{
                        LoginAndSignUp()
                    }
                }
            }

        }
    }
}

sealed class PageITOON(val route:String){
    object Login: PageITOON("Login_Page")
    object SignUp: PageITOON("Signup_Page")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginContent(email:String,onEmailChange:(String)->Unit,
                 password:String,onPassChange:(String)->Unit){
    Column (modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
        , horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Center) {
        Text(text = "อีเมล",fontSize = 16.sp,modifier = Modifier.padding(bottom = 5.dp))
        BasicTextField(value = email , onValueChange = onEmailChange ,modifier = Modifier
            .width(400.dp)
            .border(
                border = BorderStroke(1.dp, color = Color.Red),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(20.dp)
            , keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email
            )
        )
        Text(text = "รหัสผ่าน",fontSize = 16.sp,modifier = Modifier.padding(bottom = 5.dp,top = 5.dp))
        BasicTextField(value = password , onValueChange = onPassChange ,modifier = Modifier
            .width(400.dp)
            .border(
                border = BorderStroke(1.dp, color = Color.Red),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(20.dp),
            visualTransformation = PasswordVisualTransformation()
            , keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            )
        )
    }
}
@Composable
fun LoginPage(navHostController: NavHostController)
{   var email by rememberSaveable {
    mutableStateOf("")
}
    var password by rememberSaveable{ mutableStateOf("") }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val createLogin = API.create()
    val contextForToast = LocalContext.current.applicationContext
    var usersItems = remember{ mutableStateListOf<LoginClass>() }
    lateinit var sharePreferences:SharedPreferencesManager
    sharePreferences = SharedPreferencesManager(context = contextForToast)

    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    LaunchedEffect(lifecycleState){
        when(lifecycleState){
            Lifecycle.State.DESTROYED -> {}
            Lifecycle.State.INITIALIZED -> {}
            Lifecycle.State.CREATED -> {}
            Lifecycle.State.STARTED -> {}
            Lifecycle.State.RESUMED -> {
                if(sharePreferences.isLoggedIn){
                    navHostController.navigate(BottomBar.FirstPage.route)
                }
                if(!sharePreferences.userEmail.isNullOrEmpty()){
                    email = sharePreferences.userEmail?:"1"
                }
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Image(painter = painterResource(id = R.drawable.logo), contentDescription = null ,modifier = Modifier.size(250.dp), contentScale = ContentScale.Fit)
        Text(text = "เข้าสู่ระบบ",fontSize = 25.sp, fontWeight = FontWeight.Bold)

        LoginContent(
            email = email,
            onEmailChange = { email = it },
            password = password,
            onPassChange = {password = it}
        )

        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            , horizontalAlignment = Alignment.End){
            TextButton(onClick = {}) {
                Text(text = "ลืมรหัสผ่าน",color = Color.Black,modifier = Modifier.alpha(0.5f))
            }
        }
        Button(onClick = {
            keyboardController?.hide()
            focusManager.clearFocus()
            createLogin.login(email,password)
                .enqueue(object :Callback<LoginClass>{
                    @SuppressLint("RestrictedApi")
                    override fun onResponse(
                        call: Call<LoginClass>,
                        response: Response<LoginClass>
                    ) {
                        if (response.isSuccessful) {
                            val loginContent = response.body()
                            if (loginContent!!.success == 1) {
                                sharePreferences.isLoggedIn = true
                                sharePreferences.userEmail = loginContent.email
                                println(sharePreferences.isLoggedIn)

                                val intent = Intent(contextForToast, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                contextForToast.startActivity(intent)
                            }else{
                            Toast.makeText(contextForToast,"Email or Password is incorrect.",
                                Toast.LENGTH_LONG).show()
                        }
                    }else{
                        usersItems.clear()
                        Toast.makeText(contextForToast,"User not found!",
                            Toast.LENGTH_LONG).show()
                    }
                }

                    override fun onFailure(call: Call<LoginClass>, t: Throwable) {
                        Toast.makeText(contextForToast,"Error on Failure" + t.message,
                            Toast.LENGTH_LONG).show()
                    }
                })
        },colors = ButtonDefaults.buttonColors(Color(184,0,0)),modifier = Modifier
            .width(129.dp)
            , shape = RoundedCornerShape(10.dp)
        ) {
            Text(text = "เข้าสู่ระบบ",color = Color.White)
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Text(text = "หรือ",modifier = Modifier.alpha(0.5f))
        TextButton(onClick = {
            navHostController.navigate(PageITOON.SignUp.route)
        },modifier = Modifier
            .width(129.dp)
        ) {
            Text(text = "สมัครสมาชิก",color = Color.Black)
        }
    }
}



@Composable
fun LoginAndSignUp(){
    val navController = rememberNavController()
    var context = LocalContext.current.applicationContext
    lateinit var share : SharedPreferencesManager
    share = SharedPreferencesManager(context)
    NavHost(navController = navController,startDestination = PageITOON.Login.route){
        composable(PageITOON.Login.route){
            LoginPage(navController)
        }
        composable(PageITOON.SignUp.route){
            Signup(navController)
        }
    }
}

