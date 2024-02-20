package com.project.itoon.firstpageapi

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.Date

data class edithistory (
    @Expose
    @SerializedName("cartoonId") val cartoonId:Int,

    @Expose
    @SerializedName("userId") val userId:Int,

    @Expose
    @SerializedName("episodeId") val episodeId:Int,

    @Expose
    @SerializedName("viewDate") val viewDate:Date,

    @Expose
    @SerializedName("epnum") val epnum:Int
)