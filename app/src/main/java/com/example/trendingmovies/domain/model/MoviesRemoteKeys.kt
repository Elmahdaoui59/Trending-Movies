package com.example.trendingmovies.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies_remote_keys")
data class MoviesRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val movieKey: Long,
    val prevKey: Int?,
    val nextKey: Int?
)
