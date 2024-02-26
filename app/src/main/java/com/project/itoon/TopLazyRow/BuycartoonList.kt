package com.project.itoon.TopLazyRow

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.project.itoon.favoritebutton.ShowFavClass
import com.project.itoon.firstpageapi.Cartoon

data class BuycartoonList(
    @Expose
    @SerializedName("cartoonlist") val cartoonlist: Cartoon,
    @Expose
    @SerializedName("showlist") val showlist: BoughtCartoon
)
