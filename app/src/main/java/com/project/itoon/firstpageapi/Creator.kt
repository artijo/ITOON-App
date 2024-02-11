package com.project.itoon.firstpageapi

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Creator(
    @Expose
    @SerializedName("id") val id:Int,

    @Expose
    @SerializedName("userid") val userid:Int,

): Parcelable