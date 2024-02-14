package com.project.itoon.firstpageapi

import com.project.itoon.cartoonPage.CartoonAllEp
import com.project.itoon.cartoonPage.imgEp
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface CartoonAPI {

    @GET("allCartoon")
    fun retrieveCartoon(): Call<List<Cartoon>>

    @GET("recCartoon")
    fun recAllCartoon(): Call<List<Cartoon>>

    @GET("Cartoon/{cartoonid}")
    fun getCartoon(
        @Path("cartoonid")id:Int
    ):Call<Cartoon>

//Get All Ep Cartoon
    @GET("getAllEpCartoon/{cartoonId}")
    fun getAEC(
        @Path("cartoonId")id:Int
    ):Call<List<CartoonAllEp>>



    @GET("getImageEp/{epId}")
    fun getImgCartoon(
        @Path("epId") id: Int
    ):Call<List<imgEp>>

    companion object{
        fun create():CartoonAPI{
            val cartoonClient:CartoonAPI = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CartoonAPI::class.java)
            return cartoonClient
        }
    }
}