package com.project.itoon.favoritebutton

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.project.itoon.firstpageapi.Cartoon
import java.util.Date

data class ShowFavClass (
    @Expose
    @SerializedName("id") val id:Int,
    @Expose
    @SerializedName("userId") val userId:Int,
    @Expose
    @SerializedName("cartoonId") val cartoonId: Cartoon,
    @Expose
    @SerializedName("favoriteDate") val favoriteDate: Date,
    @Expose
    @SerializedName("cartoon") val cartoon:Cartoon,
)