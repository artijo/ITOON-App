package com.project.itoon.firstpageapi

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface CartoonAPI {
    @GET("allCartoon") ///    Call NodeJS : Query
    fun retrieveCartoon(): Call<List<Cartoon>>
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