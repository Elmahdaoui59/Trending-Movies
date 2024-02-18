package com.example.trendingmovies.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.trendingmovies.domain.model.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)

    @Query("select * from movies where id in (:ids)")
    suspend fun getMoviesByIds(ids: List<Int>): List<Movie>
    @Query("select * from movies")
    fun getMovies(): PagingSource<Int, Movie>

    @Query("delete from movies")
    fun clearMovies()
}