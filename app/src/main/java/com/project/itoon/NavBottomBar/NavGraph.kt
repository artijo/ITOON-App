package com.project.itoon.NavBottomBar

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.project.itoon.FirstPage
import com.project.itoon.LoginAndSignUp.LoginPage
import com.project.itoon.LoginAndSignUp.PageITOON
import com.project.itoon.LoginAndSignUp.Signup
import com.project.itoon.LoginAndSignUp.startLoginActivity
import com.project.itoon.Setting.SettingClass
import com.project.itoon.Setting.Settingpage
import com.project.itoon.Setting.SharedPreferencesManager
import com.project.itoon.cartoonPage.CartoonPage
import com.project.itoon.cartoonPage.SelectPage




@Composable
fun NavGraph(navHostController: NavHostController) {
    val context = LocalContext.current.applicationContext
    lateinit var share : SharedPreferencesManager
    share = SharedPreferencesManager(context)
    val navController = rememberNavController()
    NavHost(navController = navHostController, startDestination = BottomBar.FirstPage.route , route = "MainPage") {
        composable(route = BottomBar.FirstPage.route) {
            FirstPage(navHostController)
        }
        composable(route = BottomBar.MyCartoon.route) {
                MyCarToonPage(navController)
        }

        composable(route = BottomBar.Coin.route) {
            CoinPage(navHostController)
        }
        composable(route = BottomBar.ETC.route) {
            ETCPage(navHostController)
        }
        composable(route = CartoonPage.CartoonEP.route){
            SelectPage(navHostController)
        }
        composable(route = SettingClass.Setting.route){
            Settingpage(navHostController)
        }
        composable(route = PageITOON.Login.route) {
            LoginPage(navHostController)
        }
        composable(route = PageITOON.SignUp.route) {
            Signup(navHostController)
        }
        composable(route = PageITOON.SignUp.route) {
            Signup(navHostController)
        }
        composable(route = BottomBar.SearchPage.route) {
            SearchPage(navHostController)
        }
        composable(route = BottomBar.RegcreatorPage.route){
            creatorREG(navHostController)
        }


    }
}
@SuppressLint("RestrictedApi")
@Composable
fun MyBottomBar(navHostController: NavHostController, contextForToast: Context){
    val navigationItems = listOf(
        BottomBar.FirstPage,
        BottomBar.MyCartoon,
        BottomBar.Coin,
        BottomBar.ETC,
    )
    var selectScreen by remember {
        mutableStateOf(0)
    }


    NavigationBar(modifier = Modifier.background(color = Color.Green)) {
        navigationItems.forEachIndexed { index, bottomBar ->
            NavigationBarItem(selected = false,
                onClick = {
                    if (navHostController.currentBackStack.value.size>=4){
                        navHostController.popBackStack()
                    }
                    navHostController.navigate(bottomBar.route)
                     }, label = { Text(text = bottomBar.name) } ,icon = {
                    Icon(painter = painterResource(id = bottomBar.icon),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    ) })
        }
    }
}



@SuppressLint("RestrictedApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(navController: NavHostController, contextForToast: Context){
//    val data = navController.previousBackStackEntry?.savedStateHandle?.get<Toptextclass>("data")?:
//    Toptextclass("Hi")
    lateinit var share :SharedPreferencesManager
    share =SharedPreferencesManager(contextForToast)
    var toptext by remember {
        mutableStateOf("ITOON")
    }
    val toptextstate by navController.currentBackStackEntryAsState()
    val toptextnext = toptextstate?.destination
    LaunchedEffect(key1 = toptextnext){
        toptextnext?.let {
            item -> toptext=when(item.route){
                BottomBar.Favorite.route->BottomBar.Favorite.name
                BottomBar.Coin.route->BottomBar.Coin.name
                BottomBar.ETC.route->BottomBar.ETC.name
                BottomBar.MyCartoon.route->BottomBar.MyCartoon.name
                SettingClass.Setting.route->SettingClass.Setting.name
                else->"ITOON"
            }
        }
    }
    if(navController.currentDestination?.route != SettingClass.Setting.route){
    TopAppBar(
        title = { Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = toptext,
                    letterSpacing = 1.75.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            PrintCurrentRoute(navController)


        }
        }, actions = {
            IconButton(onClick = {
                navController.navigate(BottomBar.MyCartoon.route)
                                 },modifier = Modifier.size(20.dp)) {
                Icon(imageVector = Icons.Default.Favorite, contentDescription = null
                ,tint= Color.White
                )
            }
            Spacer(modifier = Modifier.padding(start = 10.dp))
            IconButton(onClick = {
                navController.navigate(BottomBar.SearchPage.route)
                                 },modifier = Modifier.size(20.dp)) {
                Icon(imageVector = Icons.Default.Search, contentDescription = null
                ,tint = Color.White)
            }
        }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(184,0,0))
    )
    }
    else{
        TopAppBar(
            title = { Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = toptext,
                    letterSpacing = 1.75.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
                PrintCurrentRoute(navController)


            }
            }, actions = {
                if (!share.isLoggedIn){
                    IconButton(onClick = {startLoginActivity(contextForToast)},
                        modifier = Modifier.size(20.dp))
                    {
                        Icon(imageVector = Icons.Filled.Login, contentDescription = null,tint = Color.White )
                    }
                }else{
                    IconButton(onClick = {
                        share.clearUserAll()
                        Toast.makeText(contextForToast,"Logout Successfully.",
                            Toast.LENGTH_LONG).show()
                        navController.navigate(BottomBar.FirstPage.route)
                    },modifier = Modifier.size(20.dp)) {
                        Icon(imageVector = Icons.Filled.Logout, contentDescription = null,tint = Color.White )
                    }
                }
            }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(184,0,0))
        )
    }
}


@SuppressLint("RestrictedApi", "StateFlowValueCalledInComposition")
@Composable
fun MainScreen(){
    val contextForToast = LocalContext.current.applicationContext
    val navHostControllers = rememberNavController()
    Scaffold(
        topBar = {
                MyTopAppBar(navHostControllers, contextForToast)
        },
        bottomBar = {
                MyBottomBar(navHostControllers, contextForToast)
        }
    ) {
            paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NavGraph(navHostController = navHostControllers)

        }
    }
}

@Composable
fun PrintCurrentRoute(navController: NavHostController) {
    val currentRoute = navController.currentDestination?.route
    println("Current Route: $currentRoute")
}