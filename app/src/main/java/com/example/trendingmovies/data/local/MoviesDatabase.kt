package com.example.trendingmovies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.trendingmovies.data.local.dao.MovieDao
import com.example.trendingmovies.data.local.dao.MoviesRemoteKeysDao
import com.example.trendingmovies.domain.model.Movie
import com.example.trendingmovies.domain.model.MoviesRemoteKeys

@Database(entities = [Movie::class, MoviesRemoteKeys::class], version = 3)
@TypeConverters(GenreIdsConverter::class)
abstract class MoviesDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun remoteKeysDao(): MoviesRemoteKeysDao

}