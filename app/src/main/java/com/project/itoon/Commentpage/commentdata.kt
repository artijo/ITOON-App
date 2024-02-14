package com.project.itoon.Commentpage

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class commentdata(
    @Expose
    @SerializedName("episodeId") val episodeId:Int,

    @Expose
    @SerializedName("userId") val userId:Int,

    @Expose
    @SerializedName("content") val content:String,
):Parcelable