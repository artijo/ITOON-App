package com.project.itoon.firstpageapi

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.project.itoon.LoginAndSignUp.User
import kotlinx.parcelize.Parcelize

@Parcelize
data class Creator(
    @Expose
    @SerializedName("id") val id:Int,

    @Expose
    @SerializedName("userId") val userid:Int,
    @Expose
    @SerializedName("createAt")val createdAt: String,
    @Expose
    @SerializedName("updatedAt")val updatedAt: String,
    @Expose
    @SerializedName("deletedAt")val deletedAt: String?,
    @Expose
    @SerializedName("user")val user: User

): Parcelable