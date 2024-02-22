package com.project.itoon.NavBottomBar

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.project.itoon.cartoonPage.CartoonPage
import com.project.itoon.firstpageapi.Cartoon
import com.project.itoon.firstpageapi.CartoonAPI
import okhttp3.HttpUrl.Companion.toHttpUrl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("RestrictedApi")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchPage(navHostController: NavHostController){
    val createClient = CartoonAPI.create()
    var cartoonItemsList = remember {
        mutableStateListOf<Cartoon>()
    }
    val contextForToast = LocalContext.current.applicationContext
    var textFieldName by remember { mutableStateOf("") }

    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()
//    val (focusRequester) = FocusRequester.createRefs() ////Keyboard Action
    LaunchedEffect(lifecycleState) {
        when (lifecycleState) {
            Lifecycle.State.DESTROYED -> {}
            Lifecycle.State.INITIALIZED -> {}
            Lifecycle.State.CREATED -> {}
            Lifecycle.State.STARTED -> {}
            Lifecycle.State.RESUMED -> {
                showAllCartoon(cartoonItemsList, contextForToast)
            }
        }
    }
    Column (horizontalAlignment = Alignment.CenterHorizontally){
        Spacer(modifier = Modifier.padding(5.dp))
        OutlinedTextField(
            modifier = Modifier
                .width(280.dp)
                .height(50.dp),
            shape = RoundedCornerShape(10.dp),
            value = textFieldName,
            onValueChange = { textFieldName = it },

            trailingIcon =  {Icon(imageVector = Icons.Default.Search, contentDescription = null,
                Modifier.clickable {
                    if (textFieldName.trim().isEmpty()) {
                        showAllCartoon(cartoonItemsList, contextForToast)
                    } else {
                        cartoonItemsList.clear()
                        createClient.SearchCartoon(textFieldName)
                            .enqueue(object : Callback<List<Cartoon>> {
                                override fun onResponse(
                                    call: Call<List<Cartoon>>,
                                    response: Response<List<Cartoon>>
                                ) {

                                    if (response.isSuccessful) {
                                        Toast.makeText(
                                            contextForToast,
                                            "${response!!.body()?.size}", Toast.LENGTH_LONG
                                        ).show()
                                        response!!.body()?.forEach {
                                            Log.i("Data",it.toString())
                                            cartoonItemsList.add(it)
                                        }

                                    } else {
                                        Toast.makeText(
                                            contextForToast,
                                            "Cartoon not Found", Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }

                                override fun onFailure(call: Call<List<Cartoon>>, t: Throwable) {
                                    Toast.makeText(
                                        contextForToast, "Error onFailure at search " + t.message,
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            })
                    }
                })
            }
        )
        Spacer(modifier = Modifier.padding(5.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            itemsIndexed(
                items = cartoonItemsList,
                itemContent = { index, item ->
                    var clickCartoon : Cartoon
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 8.dp)
                            .fillMaxWidth()
                            .height(100.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White,
                        ),
                        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
                        onClick = {
                            if (navHostController.currentBackStack.value.size >= 2){
                                navHostController.popBackStack()
                            }
                            clickCartoon = item
                            navHostController.currentBackStackEntry?.savedStateHandle?.set(
                                "data",
                                clickCartoon
                            )
                            navHostController.navigate(CartoonPage.CartoonEP.route)
                        }) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .height(Dp(value = 130f))
                                .padding(end = 16.dp)
                            ,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val urltext = item.thumbnail
                            if (urltext.startsWith("uploads")){
                                val replace = urltext.replace("\\","/")
                                item.thumbnail = "http://10.0.2.2:3000/"+"$replace"
                                val pathUrl = item.thumbnail.toHttpUrl()
                                Image(
                                    painter = rememberAsyncImagePainter(pathUrl),
                                    contentDescription = item.name,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .width(100.dp)
                                        .height(100.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                )
                                }else{
                            Image(
                                painter = rememberAsyncImagePainter(item.thumbnail),
                                contentDescription = item.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(100.dp)
                                    .clip(RoundedCornerShape(10.dp))
                            )}

                            Text(
                                text = "${item.name}\n" ,
                                Modifier
                                    .weight(0.85f)
                                    .padding(start = 10.dp),
                                textAlign = TextAlign.Justify,
                                fontSize = 15.sp
                            )



                        }
                    }
                })   }
    }



}

fun showAllCartoon(cartoonItemsList: MutableList<Cartoon>, context: Context) {
    val createClient = CartoonAPI.create()
    createClient.retrieveCartoon()
        .enqueue(object : Callback<List<Cartoon>> {
            override fun onResponse(
                call: Call<List<Cartoon>>,
                response: Response<List<Cartoon>>
            ) {
                cartoonItemsList.clear()
                response.body()?.forEach {
                    cartoonItemsList.add(
                        Cartoon(
                            it.id,
                            it.name,
                            it.description,
                            it.releaseDate,
                            it.thumbnail,
                            it.totalEpisodes,
                            it.creatorId,
                            it.genreId,
                            it.genres,
                            it.creator,
                            it.paid,
                            it.price
                        )
                    )
                }
            }

            override fun onFailure(call: Call<List<Cartoon>>, t: Throwable) {
                Toast.makeText(context, "Err onFailure atshowallfun" + t.message, Toast.LENGTH_LONG)
                    .show()
            }
        })

}