package com.project.itoon.firstpageapi

import com.project.itoon.Config
import com.project.itoon.TopLazyRow.allhistory
import com.project.itoon.cartoonPage.CartoonAllEp
import com.project.itoon.cartoonPage.imgEp
import com.project.itoon.favoritebutton.Status
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.DELETE
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

    @GET("searchCartoon/{name}")
    fun SearchCartoon(
        @Path("name")name:String
    ):Call<List<Cartoon>>

    @GET("getImageEp/{epId}")
    fun getImgCartoon(
        @Path("epId") id: Int
    ):Call<List<imgEp>>

    @GET("fav/{id}/{cartoonId}")
    fun isFav(
        @Path("id") id: Int,
        @Path("cartoonId") cartoonId: Int
    ): Call<Status>


    @GET("favInsert/{id}/{cartoonId}")
    fun addFav(
        @Path("id") id: Int,
        @Path("cartoonId") cartoonId: Int
    ): Call<Status>

    @DELETE("fav/{id}/{cartoonId}")
    fun unFav(
        @Path("id") id: Int,
        @Path("cartoonId") cartoonId: Int
    ): Call<Status>



    @GET("edithistory/{uid}/{cid}/{epnum}")
    fun updatehistory(
        @Path("uid") uid:String,
        @Path("cid") cid:String,
        @Path("epnum") epnum:String,
    ):Call<edithistory>

    @GET("allhistory/{uid}")
    fun allhistory(
        @Path("uid") uid: String
    ):Call<List<allhistory>>

    companion object{
        fun create():CartoonAPI{
            val cartoonClient:CartoonAPI = Retrofit.Builder()
                .baseUrl(Config().APIBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CartoonAPI::class.java)
            return cartoonClient
        }
    }
}