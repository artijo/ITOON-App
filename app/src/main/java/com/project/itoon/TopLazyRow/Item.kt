package com.project.itoon.TopLazyRow

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.project.itoon.firstpageapi.Cartoon

class Item (
    @Expose
    @SerializedName("cartoonlist")val cartoonlist : Cartoon,
    @Expose
    @SerializedName("eplist")val eplist : allhistory
){
}