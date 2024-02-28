package com.project.itoon.cartoonPage

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class imgEp(
    @Expose
    @SerializedName("id") val id:Int,

    @Expose
    @SerializedName("page") val page:Int,

    @Expose
    @SerializedName("url") val pathUrl:String,
)
