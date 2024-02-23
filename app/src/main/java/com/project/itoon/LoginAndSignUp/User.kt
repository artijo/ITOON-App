package com.project.itoon.LoginAndSignUp

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    @Expose
    @SerializedName("id") val id:Int,

    @Expose
    @SerializedName("email") val email:String,

    @Expose
    @SerializedName("name") val name:String,

    @Expose
    @SerializedName("password") val password:String,

    @Expose
    @SerializedName("phone") val phone:String,

    @Expose
    @SerializedName("coin") val coin:Int
): Parcelable

data class Creator(
    @Expose
    @SerializedName("id") val id:Int,

    @Expose
    @SerializedName("userId") val userId:Int,

    @Expose
    @SerializedName("status") val status:String,
)