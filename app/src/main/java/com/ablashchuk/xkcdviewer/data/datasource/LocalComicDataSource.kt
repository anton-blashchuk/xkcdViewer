package com.ablashchuk.xkcdviewer.data.datasource

import com.ablashchuk.xkcdviewer.data.access.FavoriteComicDao
import com.ablashchuk.xkcdviewer.data.model.Comic

/** @author Blashchuk Anton */
class LocalComicDataSource(private val dao: FavoriteComicDao) {
    suspend fun getFavorite(num: Int): Comic? = dao.getFavoriteComic(num)
    suspend fun getAllFavorites() = dao.getAllFavorites()
    suspend fun saveFavorite(comic: Comic) = dao.insertComic(comic)
    suspend fun removeFavorite(comic: Comic) = dao.deleteComic(comic)
}