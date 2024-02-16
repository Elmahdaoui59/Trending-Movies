package com.example.trendingmovies.presentation.movies

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun MoviesListScreen(
    state: MoviesUiState,
    innerPadding: PaddingValues
) {

    if (state.movies?.isNotEmpty() == true) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            items(
                state.movies,
                key = {
                    it.id
                }
            ) {movie ->
                MovieItemLayout(movie = movie)
                Spacer(modifier = Modifier.height(20.dp))
            }
        }

    }
}