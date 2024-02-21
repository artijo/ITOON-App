package com.project.itoon.favoritebutton

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FavClass(
    @Expose
    @SerializedName("id") val id:Int,

    @Expose
    @SerializedName("cartoonId") val cartoonId:Int
)
