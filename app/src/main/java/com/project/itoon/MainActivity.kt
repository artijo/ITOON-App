package com.project.itoon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.project.itoon.LoginAndSignUp.LoginPage
import com.project.itoon.LoginAndSignUp.Signup
import com.project.itoon.NavBottomBar.MainScreen
import com.project.itoon.favoritebutton.FavoriteButton
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
                    //                    MenuLazyrow()
                    MainScreen()
//                    FavoriteButton(1001)
//                    TestPayment()
//                    CartoonThisChapter()
//                    if you want to call first page just use TestCall() function
//                    FirstPage()
                }
            }
        }
    }
}

sealed class PageITOON(val route:String){
    object Login:PageITOON("Login_Page")
    object SignUp:PageITOON("Signup_Page")
}



@Composable
fun TestComposable(){
    val navController = rememberNavController()
    NavHost(navController = navController,startDestination = PageITOON.Login.route){
        composable(PageITOON.Login.route){
            LoginPage(navController)
        }
        composable(PageITOON.SignUp.route){
            Signup(navController)
        }
    }
}

@Composable
fun TestScreen(){
    var email by rememberSaveable{ mutableStateOf("") }
    var password by rememberSaveable{ mutableStateOf("") }
    var name by rememberSaveable{ mutableStateOf("") }
    var confirmpass by rememberSaveable{ mutableStateOf("") }

//    LoginPage(email = email, onEmailChange = { email = it }, password = password, onPassChange = {password = it})

//    Signup(
//        name = name,
//        onNamechage = { name = it },
//        email = email,
//        onEmailchage = {email = it},
//        password = password,
//        onPasswordchage = { password = it },
//        confirmpass = confirmpass,
//        onConfirmchage = { confirmpass = it }
//    )
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
    }
}


//eufa test bottom bar & screen funciton


