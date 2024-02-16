package com.project.itoon.firstpageapi

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class Genres(
    @Expose
    @SerializedName("id") val id: Int,
    @Expose
    @SerializedName("name")val name: String,
    @Expose
    @SerializedName("createdAt")val createdAt: String,
    @Expose
    @SerializedName("updatedAt")val updatedAt: String,
    @Expose
    @SerializedName("deletedAt")val deletedAt: String?
):Parcelable
