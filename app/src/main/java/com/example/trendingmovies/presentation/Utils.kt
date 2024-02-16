package com.example.trendingmovies.presentation

import com.example.trendingmovies.presentation.common.UiEvent
import com.example.trendingmovies.presentation.navigation.Screen
import kotlinx.coroutines.flow.MutableSharedFlow


fun getScreenTitleByRoute(route: String): String {
    return  when {
        route.contains(Screen.MoviesScreen.route) -> Screen.MoviesScreen.title
        route.contains(Screen.MovieDetailScreen.route) -> Screen.MovieDetailScreen.title
        else -> ""
    }
}

val eventFlow: MutableSharedFlow<UiEvent> by lazy {
    MutableSharedFlow()
}