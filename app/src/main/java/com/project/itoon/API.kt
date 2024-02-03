package com.project.itoon

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface API {

    @FormUrlEncoded
    @POST("User")
    fun insertUser(
        @Field("email")email:String,
        @Field("name")name:String,
        @Field("password")password:String,
        @Field("phone")phone:String
    ):Call<User>
    companion object{
        fun create():API{
            val usr : API = Retrofit.Builder()
                .baseUrl("http://10.0.0.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(API::class.java)
            return usr
        }
    }
}