package com.project.itoon.firstpageapi

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

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
    @SerializedName("thumbnail") var thumbnail:String,

    @Expose
    @SerializedName("totalEpisodes") val totalEpisodes:Int,

    @Expose
    @SerializedName("creatorId") val creatorId: Int,

    @Expose
    @SerializedName("genreId") val genreId :Int,

    @Expose
    @SerializedName("genres") val genres :Genres,

    @Expose
    @SerializedName("creator")val creator:Creator?,

    ): Parcelable

