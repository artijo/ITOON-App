package com.project.itoon.LoginAndSignUp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginClass (
    @Expose
    @SerializedName("success")val success:Int,

    @Expose
    @SerializedName("email")val email:String,

    @Expose
    @SerializedName("id")val id:Int
)