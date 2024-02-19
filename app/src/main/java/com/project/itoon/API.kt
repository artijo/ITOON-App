package com.project.itoon

import com.project.itoon.Commentpage.commentdata
import com.project.itoon.LoginAndSignUp.LoginClass
import com.project.itoon.LoginAndSignUp.User
import com.project.itoon.Setting.Profile
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
    @GET("users/{id}")
    fun getUSerbyID(
       @Path("id") id:Int
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
    ):Call<LoginClass>

//Comment
    @GET("comments")
    fun getComment():Call<List<commentdata>>

    @GET("comments/{cid}/{eid}")
    fun getEpcommnet(
        @Path("cid") cartoonid:Int,
        @Path("eid") episodeId:Int,
    ):Call<List<commentdata>>
    @FormUrlEncoded
    @POST("insertcomment")
    fun insertComment(
        @Field("content") content:String,
        @Field("userId") userid: Int,
        @Field("episodeId") epid: Int
    ):Call<commentdata>

//    updateprofile
    @PUT("profile/{id}")
    fun updateProfile(
    @Path("id") id:String,
    @Field("email") email: String,
    @Field("name") name: String
    ):Call<Profile>



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