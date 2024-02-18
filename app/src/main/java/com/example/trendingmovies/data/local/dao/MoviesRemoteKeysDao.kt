package com.example.trendingmovies.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.trendingmovies.domain.model.MoviesRemoteKeys

@Dao
interface MoviesRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKeys(keys: List<MoviesRemoteKeys>)

    @Query("select * from movies_remote_keys where movieKey = :movieId")
    suspend fun getMovieRemoteKey(movieId: Long): MoviesRemoteKeys
    @Query("delete from movies_remote_keys")
    suspend fun clearRemoteKeys()

}