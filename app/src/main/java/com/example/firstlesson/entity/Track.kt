package com.example.firstlesson.entity

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes

data class Track(
    val id: Int,
    val name: String?,
    val author: String?,
    @DrawableRes val cover: Int,
    @RawRes val music: Int
)
