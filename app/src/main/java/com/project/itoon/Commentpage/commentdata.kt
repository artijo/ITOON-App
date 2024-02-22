package com.project.itoon.Commentpage

import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.project.itoon.LoginAndSignUp.User
import com.project.itoon.firstpageapi.Cartoon
import kotlinx.parcelize.Parcelize
import java.util.Objects


@Parcelize
data class commentdata(
    @Expose
    @SerializedName("content") val content:String,

    @Expose
    @SerializedName("userId") val userId:Int,

    @Expose
    @SerializedName("user") val user:User,

    @Expose
    @SerializedName("episodeId") val episodeId:Int,

    @Expose
    @SerializedName("episode") val episode:Episode,

):Parcelable

