package com.ablashchuk.xkcdviewer.data.model

import com.google.gson.annotations.SerializedName
import androidx.room.Entity
import androidx.room.PrimaryKey

/** @author Blashchuk Anton */
@Entity(tableName = "comics")
data class Comic(
    val month: String,
    @PrimaryKey val num: Int,
    val link: String,
    val year: String,
    val news: String,
    @SerializedName("safe_title")
    val safeTitle: String,
    val transcript: String,
    val alt: String,
    val img: String,
    val title: String,
    val day: String,
)