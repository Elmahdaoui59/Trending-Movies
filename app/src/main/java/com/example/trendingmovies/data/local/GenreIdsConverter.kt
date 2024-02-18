package com.example.trendingmovies.data.local

import androidx.room.TypeConverter

class GenreIdsConverter {
    @TypeConverter
    fun fromList(genreIds: List<Int>): String {
        return genreIds.joinToString(separator = ",")
    }

    @TypeConverter
    fun toList(genreIdsString: String): List<Int> {
        return genreIdsString.split(",").map { it.toInt() }
    }
}
