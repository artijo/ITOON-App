package com.project.itoon.favoritebutton

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.project.itoon.favoritebutton.ShowFavClass
import com.project.itoon.firstpageapi.Cartoon

data class ItemFav(
    @Expose
    @SerializedName("cartoonlist") val cartoonlist:Cartoon,
    @Expose
    @SerializedName("showfavlist") val showfavlist:ShowFavClass
) {
}