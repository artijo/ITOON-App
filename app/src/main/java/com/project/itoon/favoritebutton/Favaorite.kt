package com.project.itoon.favoritebutton

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.project.itoon.Setting.SharedPreferencesManager
import com.project.itoon.firstpageapi.CartoonAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("UnrememberedMutableState")
@Composable
fun FavoriteButton(cartoonId: Int, cartoonName: String){
    val contextForToast = LocalContext.current.applicationContext
    lateinit var sharedPreferences: SharedPreferencesManager
    sharedPreferences = SharedPreferencesManager(context = contextForToast)
    val userId :Int = sharedPreferences.userId
    val createClient = CartoonAPI.create()
    var status by remember { mutableIntStateOf(0) }

    LaunchedEffect(true) {
        createClient.isFav(userId, cartoonId)
            .enqueue(object: Callback<Status> {
                override fun onResponse(call: Call<Status>, response: Response<Status>) {
                    val statusResponse = response.body()
                    status = statusResponse!!.message
                }
                override fun onFailure(call: Call<Status>, t: Throwable) {
                    Toast.makeText(contextForToast, "Error onFailure $t", Toast.LENGTH_LONG).show()
                }
            })
    }

    Box(
      modifier = Modifier
          .padding(10.dp)
          .size(50.dp)

    ){
        IconButton(
            onClick = {
                when(status){
                    0 -> {
                        status = 1
                        createClient.addFav(userId,cartoonId)
                            .enqueue(object: Callback<Status>{
                                override fun onResponse(
                                    call: Call<Status>,
                                    response: Response<Status>
                                ) {
                                    if(response.isSuccessful){
                                        Toast.makeText(contextForToast,"Favorite $cartoonName",Toast.LENGTH_LONG).show()
                                    }else{
                                        Toast.makeText(contextForToast,"can't insert favorite $cartoonName",Toast.LENGTH_LONG).show()
                                    }
                                }

                                override fun onFailure(call: Call<Status>, t: Throwable) {
                                    Toast.makeText(contextForToast,"Error onFailure",Toast.LENGTH_SHORT).show()
                                }
                        })
                    }
                    1 -> {
                        status = 0
                        createClient.unFav(userId,cartoonId)
                            .enqueue(object: Callback<Status>{
                                override fun onResponse(
                                    call: Call<Status>,
                                    response: Response<Status>
                                ) {
                                    if(response.isSuccessful){
                                        Toast.makeText(contextForToast,"UnFavorite $cartoonName",Toast.LENGTH_SHORT).show()
                                    }else{
                                        Toast.makeText(contextForToast,"fail to UnFavorite $cartoonName",Toast.LENGTH_SHORT).show()
                                    }
                                }
                                override fun onFailure(call: Call<Status>, t: Throwable) {
                                    Toast.makeText(contextForToast,"Error onFailure",Toast.LENGTH_SHORT).show()
                                }
                        })
                    }
                }
        }){
            when(status){
                0 -> {
                    Icon(
                        Icons.Outlined.Favorite,
                        contentDescription = "love icon",
                        tint = Color.Gray,
                        modifier = Modifier
                            .size(50.dp)
                            .alpha(0.7f)
                    )
                }
                1 -> {
                    Icon(
                        Icons.Filled.Favorite,
                        contentDescription = "love icon",
                        tint = Color.Red,
                        modifier = Modifier.size(50.dp)
                    )
                }
            }
        }
    }
}