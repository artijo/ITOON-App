package com.project.itoon.NavBottomBar

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import com.project.itoon.LoginAndSignUp.User
import com.project.itoon.firstpageapi.Cartoon
import com.project.itoon.firstpageapi.CartoonAPI
import com.project.itoon.firstpageapi.Creator
import com.project.itoon.firstpageapi.Genres
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
    Column {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Text(
                text = "Search:",
                fontSize = 20.sp
            )
            OutlinedTextField(
                modifier = Modifier
                    .width(230.dp)
                    .padding(10.dp),
                value = textFieldName,
                onValueChange = { textFieldName = it },
                label = { Text(text = "Cartoon name") }
            )

            Button(onClick = {
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
                                        "${response!!.body()}", Toast.LENGTH_LONG
                                    ).show()


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
            {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = Color.White)
            }


        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            var itemClick = Cartoon(0,"","","","",0,0,0, Genres(0,"","","",""),
                Creator(0,0,"","","", User(0,"","","",""))
            )
            itemsIndexed(
                items = cartoonItemsList,
                itemContent = { index, item ->
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 8.dp)
                            .fillMaxWidth()
                            .height(130.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White,
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 2.dp
                        ),
                        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
                        onClick = {
                            Toast.makeText(
                                contextForToast, "Click on ${item.name}.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .height(Dp(value = 130f))
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Name: ${item.name}\n" +
                                        "Description: ${item.description}\n",
                                Modifier.weight(0.85f)
                            )

                        }
                    }
                })   }
    }



}

private fun showAllCartoon(cartoonItemsList: MutableList<Cartoon>, context: Context) {
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
                            it.creator
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