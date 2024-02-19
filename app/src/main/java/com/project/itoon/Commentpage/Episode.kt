package com.project.itoon.Commentpage

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.project.itoon.firstpageapi.Cartoon
import kotlinx.parcelize.Parcelize

@Parcelize
data class Episode(
    @Expose
    @SerializedName("id") val epid: Int,

    @Expose
    @SerializedName("name") val epname: String,

    @Expose
    @SerializedName("episodeNumber") val epNumber: Int,

    @Expose
    @SerializedName("releaseDate") val release:String,
    @Expose
    @SerializedName("thumbnail") val epthumbnail: String,

    @Expose
    @SerializedName("cartoonId") val cartoonID: Int,
    @Expose
    @SerializedName("cartoons")val cartoons:Cartoon
) : Parcelable