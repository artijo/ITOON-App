package com.project.itoon.Setting

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Profile(
    @Expose
    @SerializedName("id")val id:String,
    @Expose
    @SerializedName("email")val email:String,
    @Expose
    @SerializedName("name") val username:String,
):Parcelable