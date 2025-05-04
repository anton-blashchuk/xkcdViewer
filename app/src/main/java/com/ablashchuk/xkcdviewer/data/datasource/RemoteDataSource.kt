package com.ablashchuk.xkcdviewer.data.datasource

import com.ablashchuk.xkcdviewer.data.access.ApiService
import com.ablashchuk.xkcdviewer.data.model.Comic

/** @author Blashchuk Anton */
class RemoteDataSource(val api: ApiService) {
    suspend fun fetchLastComic(): Comic = api.getLastComic()

    suspend fun fetchComic(num: Int): Comic = api.getComic(num)
}