package com.ablashchuk.xkcdviewer.data.access

import com.ablashchuk.xkcdviewer.data.model.Comic
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("info.0.json")
    suspend fun getLastComic(): Comic

    @GET("{num}/info.0.json")
    suspend fun getComic(@Path("num") num: Int): Comic
}