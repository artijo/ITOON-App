package com.project.itoon.TopLazyRow

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.project.itoon.LoginAndSignUp.User
import com.project.itoon.firstpageapi.Creator

data class allhistory (
    @Expose
    @SerializedName("id") val id:Int,

    @Expose
    @SerializedName("userId") val userId:Int,

    @Expose
    @SerializedName("cartoonId") val cartoonId: Int,

    @Expose
    @SerializedName("episodeId") val episodeId: Int,

    @Expose
    @SerializedName("cartoon") val cartoon: Cartoonhis,
    @Expose
    @SerializedName("user") val user: Userhis,
    @Expose
    @SerializedName("episode") val episode: Episodehis,
)

data class Cartoonhis(
    @Expose
    @SerializedName("name") val name:String,

    @Expose
    @SerializedName("creator") val creator: Creatorhis,

    @Expose
    @SerializedName("thumbnail") val thumbnail:String,

    @Expose
    @SerializedName("description") val description:String,

    @Expose
    @SerializedName("releaseDate") val releaseDate:String,

    @Expose
    @SerializedName("totalEpisodes") val totalEpisode:Int,
)

data class Creatorhis(
    @Expose
    @SerializedName("user") val user: Userhis,
)

data class Userhis(
    @Expose
    @SerializedName("name") val name:String,
)

data class Episodehis(
    @Expose
    @SerializedName("episodeNumber") val episodeNumber: Int,
)