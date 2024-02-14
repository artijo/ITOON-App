package com.project.itoon.cartoonPage

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CartoonAllEp(
    @Expose
    @SerializedName("id") val id:Int,

    @Expose
    @SerializedName("name") val name:String,

    @Expose
    @SerializedName("episodeNumber") val episodeNumber:Int,

    @Expose
    @SerializedName("releaseDate") val releaseDate:String,

    @Expose
    @SerializedName("thumbnail") val thumbnail:String,

    @Expose
    @SerializedName("cartoonId") val cartoonId:Int,
)
