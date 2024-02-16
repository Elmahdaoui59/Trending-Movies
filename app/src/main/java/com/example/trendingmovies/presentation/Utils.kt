package com.example.trendingmovies.presentation

import com.example.trendingmovies.presentation.navigation.Screen


fun getScreenTitleByRoute(route: String): String {
    return  when {
        route.contains(Screen.MoviesScreen.route) -> Screen.MoviesScreen.title
        route.contains(Screen.MovieDetailScreen.route) -> Screen.MovieDetailScreen.title
        else -> ""
    }
}