package com.project.itoon.cartoonPage

import android.annotation.SuppressLint
import android.graphics.drawable.shapes.Shape
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultShadowColor
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.project.itoon.API
import com.project.itoon.LoginAndSignUp.Creator
import com.project.itoon.LoginAndSignUp.User
import com.project.itoon.firstpageapi.Cartoon
import com.project.itoon.firstpageapi.CartoonAPI
import com.project.itoon.firstpageapi.Genres
import com.project.itoon.ui.theme.ITOONTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

sealed class CartoonPage(val route:String,val name:String){
    data object CartoonEP: CartoonPage("CartoonEP_Page","หน้าเลือกตอน")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartoonThumbnail(navController:NavHostController){
    val data = navController.previousBackStackEntry?.savedStateHandle?.get<Cartoon>("data")?:
    Cartoon(0,"","","","",0,
        Creator(0,0)
        ,0, Genres(0,"","","",""))

    var name by remember{ mutableStateOf(data.name) }
    var texturl by remember{ mutableStateOf(data.thumbnail) }
    var username = remember{ mutableStateOf("") }

    var cartoonurl by remember { mutableStateOf(
            if (texturl.isNotEmpty()) {
                Uri.parse(texturl)
            } else {
                null
            }
        )
    }


    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Box(modifier = Modifier.wrapContentSize()){
                Box(modifier = Modifier.fillMaxSize()){
                    Card(
                        Modifier
                            .wrapContentSize()
                            .height(400.dp),
                        shape = RoundedCornerShape(0.dp),
                        elevation = CardDefaults.cardElevation(0.dp),
                    ) {

                        Box(modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black)
                            .alpha(0.6f)

                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(cartoonurl),
                                contentDescription = "Image $texturl",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxSize()
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .alpha(0.6f)
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(
                                                Color.Transparent,
                                                Color.Black
                                            ),
                                            startY = 450f
                                        )
                                    )
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .padding(start = 20.dp,top = 75.dp)
                    ){
                        Box(modifier =
                        Modifier
                            .size(250.dp)
                        ) {
                            Column (
                                Modifier.fillMaxSize()
                            ){
                                Text(
                                    text = "$name",
                                    fontSize = 25.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold,
                                    lineHeight = 30.sp
                                )
                                Text(
                                    text = "${data.genres.name}",
                                    fontSize = 20.sp,
                                    color = Color.White,
                                    lineHeight = 20.sp
                                )
                            }
                        }
                    }
            }
        }
    }
}
@Preview (showBackground = true)
@Composable
fun Example(){
    ITOONTheme {

    }
}

@Composable
fun SelectPage(navHostController:NavHostController){
//    var navHostController = rememberNavController()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CartoonThumbnail(navController = navHostController)
    }
}


