package com.project.itoon.TopLazyRow

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.project.itoon.LoginAndSignUp.User
import com.project.itoon.firstpageapi.Cartoon
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
    @SerializedName("cartoon") val cartoon: Cartoon,
    @Expose
    @SerializedName("user") val user: Userhis,
    @Expose
    @SerializedName("episode") val episode: Episodehis,
)

data class Userhis(
    @Expose
    @SerializedName("name") val name:String,
)

data class Episodehis(
    @Expose
    @SerializedName("episodeNumber") val episodeNumber: Int,
)