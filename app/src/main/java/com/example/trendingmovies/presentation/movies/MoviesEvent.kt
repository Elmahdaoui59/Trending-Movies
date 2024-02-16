package com.example.trendingmovies.presentation.movies

sealed class MoviesEvent {
    object RefreshMovies: MoviesEvent()
}