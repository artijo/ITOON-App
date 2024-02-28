package com.project.itoon.favoritebutton

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Status(
    @Expose
    @SerializedName("message") val message:Int,
)
