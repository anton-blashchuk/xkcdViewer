package com.ablashchuk.xkcdviewer.data.access

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.ablashchuk.xkcdviewer.data.model.Comic

/**
 * As of now we only store favorite comics, which creates this a little bit odd naming situation
 * @author Blashchuk Anton */
@Dao
interface FavoriteComicDao {
    @Query("SELECT * FROM comics WHERE num = :num")
    suspend fun getFavoriteComic(num: Int) : Comic?

    @Query("SELECT * FROM comics")
    suspend fun getAllFavorites() : List<Comic>

    @Insert(onConflict = REPLACE)
    suspend fun insertComic(comic: Comic)

    @Delete
    suspend fun deleteComic(comic: Comic)
}