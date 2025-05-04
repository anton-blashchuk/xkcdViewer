package com.ablashchuk.xkcdviewer.data.repository

import com.ablashchuk.xkcdviewer.data.datasource.LocalDataSource
import com.ablashchuk.xkcdviewer.data.datasource.RemoteDataSource
import com.ablashchuk.xkcdviewer.data.model.Comic

/** @author Blashchuk Anton */
class ComicRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {
    suspend fun getComic(num: Int): Comic {
        return remoteDataSource.fetchComic(num)
    }

    suspend fun getLastComic(): Comic {
        return remoteDataSource.fetchLastComic()
    }

    suspend fun favoriteComic(comic: Comic) {
        localDataSource.saveFavorite(comic)
    }

    suspend fun unfavoriteComic(comic: Comic) {
        localDataSource.removeFavorite(comic)
    }

    suspend fun isFavorite(num: Int): Boolean {
        return localDataSource.getFavorite(num) != null
    }

    suspend fun getFavorites(): List<Comic> {
        return localDataSource.getAllFavorites()
    }
}