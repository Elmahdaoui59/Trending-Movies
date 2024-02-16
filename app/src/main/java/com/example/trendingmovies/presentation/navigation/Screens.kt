package com.example.trendingmovies.presentation.navigation

sealed class Screen(val route: String, val title: String) {
    object MoviesScreen: Screen("movies_screen", "Movies")
    object MovieDetailScreen: Screen("movie_detail_screen", "Movie Detail")
}