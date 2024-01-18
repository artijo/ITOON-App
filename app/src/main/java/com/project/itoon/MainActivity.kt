package com.project.itoon

import android.annotation.SuppressLint
import android.content.Context
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
       TestComposable()

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
        TestScreen()
    }
}

@SuppressLint("RestrictedApi")
@Composable
fun MyBottomBar(navHostController: NavHostController, contextForToast: Context){
    val navigationItems = listOf(
        BottomBar.Favorite,
        BottomBar.MyCartoon,
        BottomBar.Coin,
        BottomBar.ETC
    )
    var selectScreen by remember {
        mutableStateOf(0)
    }
    NavigationBar(modifier = Modifier.background(color = Color.Blue)) {
        navigationItems.forEachIndexed { index, bottomBar ->
            NavigationBarItem(selected = (selectScreen==index),
                onClick = {
                    if (navHostController.currentBackStack.value.size>=2){
                        navHostController.popBackStack()
                    }
                    selectScreen = index
                    navHostController.navigate(bottomBar.route)
                }, label = { Text(text = bottomBar.name)} ,icon = { Icon(painter = painterResource(id = bottomBar.icon), contentDescription = null, modifier = Modifier.size(20.dp)
                ) })
        }
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(navController: NavHostController, contextForToast: Context){
    TopAppBar(
        title = { Text(text = "การ์ตูนของฉัน") }, actions = {
            IconButton(onClick = { navController.navigate(BottomBar.Favorite.route) },modifier = Modifier.size(20.dp)) {
                Image(painter = painterResource(id = R.drawable.favorite), contentDescription = null)
            }
            IconButton(onClick = { navController.navigate(BottomBar.Favorite.route) },modifier = Modifier.size(20.dp)) {
                Image(painter = painterResource(id = R.drawable.favorite), contentDescription = null)
            }
        }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(184,0,0)))
}

//eufa test bottom bar & screen funciton
@Composable
fun EufaScreen(){
    val contextForToast = LocalContext.current.applicationContext
    val navHostController = rememberNavController()
    Scaffold(
        topBar = { MyTopAppBar(navHostController, contextForToast )},
        bottomBar = { MyBottomBar(navHostController, contextForToast)},
        floatingActionButtonPosition = FabPosition.End
    ) {
            paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
        }
        NavGraph(navHostController = navHostController)
    }
}

