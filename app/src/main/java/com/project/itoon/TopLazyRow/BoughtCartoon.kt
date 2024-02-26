package com.project.itoon.TopLazyRow

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.project.itoon.LoginAndSignUp.User
import com.project.itoon.firstpageapi.Cartoon
import java.util.Date

data class BoughtCartoon(
    @Expose
    @SerializedName("id") val id:Int,
    @Expose
    @SerializedName("userId") val userId:Int,
    @Expose
    @SerializedName("cartoonId") val cartoonId: Int,
    @Expose
    @SerializedName("cartoon") val cartoon: Cartoon,
)
