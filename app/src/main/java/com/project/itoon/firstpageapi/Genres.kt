package com.project.itoon.firstpageapi

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Genres(
    val id: Int,
    val name: String,
    val createdAt: String,
    val updatedAt: String,
    val deletedAt: String?
):Parcelable
