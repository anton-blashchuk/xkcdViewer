package com.ablashchuk.xkcdviewer.data.repository

import com.ablashchuk.xkcdviewer.data.datasource.LocalComicDataSource
import com.ablashchuk.xkcdviewer.data.datasource.RemoteComicDataSource
import com.ablashchuk.xkcdviewer.data.model.Comic

/** @author Blashchuk Anton */
class ComicRepository(
    private val remoteComicDataSource: RemoteComicDataSource,
    private val localComicDataSource: LocalComicDataSource
) {
    suspend fun getComic(num: Int): Comic {
        return remoteComicDataSource.fetchComic(num)
    }

    suspend fun getLastComic(): Comic {
        return remoteComicDataSource.fetchLastComic()
    }

    suspend fun favoriteComic(comic: Comic) {
        localComicDataSource.saveFavorite(comic)
    }

    suspend fun unfavoriteComic(comic: Comic) {
        localComicDataSource.removeFavorite(comic)
    }

    suspend fun isFavorite(num: Int): Boolean {
        return localComicDataSource.getFavorite(num) != null
    }

    suspend fun getFavorites(): List<Comic> {
        return localComicDataSource.getAllFavorites()
    }
}