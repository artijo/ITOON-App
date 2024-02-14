package com.project.itoon

import com.project.itoon.Commentpage.commentdata
import com.project.itoon.LoginAndSignUp.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface API {
    @GET("all")
    fun getUser():Call<List<User>>

//    getuserByID
    @GET("user/{id}")
    fun getUSerbyID(
       @Path("id") id:String
    ):Call<User>

    @FormUrlEncoded
    @POST("signup")
    fun insertUser(
        @Field("email")email:String,
        @Field("name")name:String,
        @Field("password")password:String,
        @Field("phone")phone:String
    ):Call<User>

    @GET("users/{email}/{password}")
    fun login(
        @Path("email")email:String,
        @Path("password")password: String
    ):Call<User>

    @GET("comments")
    fun getComment():Call<List<commentdata>>
//    updateprofile
    @PUT("profile/{id}")
    fun updateProfile(
    @Path("id") id:String,
    @Field("email") email: String,
    @Field("name") name: String
    ):Call<User>

    companion object{
        fun create():API{
            val usr : API = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(API::class.java)
            return usr
        }
    }
}