package com.ablashchuk.xkcdviewer.data.access

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ablashchuk.xkcdviewer.data.model.Comic

/** @author Blashchuk Anton */
@Database(entities = [Comic::class], version = 1)
abstract class ComicDatabase : RoomDatabase() {
    abstract fun favoriteComicDao(): FavoriteComicDao
}