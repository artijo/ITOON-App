package com.project.itoon.TopLazyRow

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.project.itoon.Setting.SharedPreferencesManager
import com.project.itoon.favoritebutton.ShowFavClass
import com.project.itoon.firstpageapi.Cartoon
import com.project.itoon.firstpageapi.CartoonAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun Like(navController:NavHostController){
    val creatClient = CartoonAPI.create()
    val cartoonList  = remember {
        mutableStateListOf<Cartoon>()
    }
    val contextForToast = LocalContext.current.applicationContext
    lateinit var sharedPreferenceManager : SharedPreferencesManager
    sharedPreferenceManager = SharedPreferencesManager(context = contextForToast)
    val userId by remember{ mutableStateOf(sharedPreferenceManager.userId) }
    cartoonList.clear()
    creatClient.showallfav(userId.toString())
        .enqueue(object :Callback<List<ShowFavClass>>{
            override fun onResponse(
                call: Call<List<ShowFavClass>>,
                response: Response<List<ShowFavClass>>
            ) {
                Log.i("datafav",response.body().toString())
            }

            override fun onFailure(call: Call<List<ShowFavClass>>, t: Throwable) {
                Toast.makeText(contextForToast,"Error on Failure"+t.message, Toast.LENGTH_LONG).show()
                Log.i("datafav","error")
            }
        })

}