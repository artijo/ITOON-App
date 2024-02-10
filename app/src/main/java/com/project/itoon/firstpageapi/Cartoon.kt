package com.project.itoon.firstpageapi

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.project.itoon.LoginAndSignUp.Creator
import com.project.itoon.LoginAndSignUp.User
import kotlinx.parcelize.Parcelize
import java.lang.ClassCastException
import java.util.Objects

@Parcelize
data class Cartoon(
    @Expose
    @SerializedName("id") val id:Int,

    @Expose
    @SerializedName("name") val name:String,

    @Expose
    @SerializedName("description") val description:String,

    @Expose
    @SerializedName("releaseDate") val releaseDate:String,

    @Expose
    @SerializedName("thumbnail") val thumbnail:String,

    @Expose
    @SerializedName("totalEpisodes") val totalEpisodes:Int,

    @Expose
    @SerializedName("creatorId") val creatorId: Creator,

    @Expose
    @SerializedName("genreId") val genreId :Int,

    @Expose
    @SerializedName("genres") val genres :Genres
): Parcelable

