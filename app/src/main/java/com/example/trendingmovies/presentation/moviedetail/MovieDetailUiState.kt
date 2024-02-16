package com.example.trendingmovies.presentation.moviedetail

import android.icu.text.CaseMap.Title

data class MovieDetailUiState(
    val isLoading: Boolean = false,
    val errorId: Int? = null,
    val posterPath: String? = null,
    val title: String? = null,
    val overview: String? = null,
    val releaseDate: String? = null
)
