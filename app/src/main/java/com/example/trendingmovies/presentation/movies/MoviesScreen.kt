package com.example.trendingmovies.presentation.movies

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun MoviesListScreen(
    state: MoviesUiState,
    innerPadding: PaddingValues,
    onIemClicked: (Int) -> Unit
) {

    if (state.movies?.isNotEmpty() == true) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(10.dp)
        ) {
            items(
                state.movies,
                key = {
                    it.id ?: 0
                }
            ) { movie ->
                MovieItemLayout(
                    movie = movie,
                    modifier = Modifier.clickable {
                        movie.id?.let { onIemClicked(it) }
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }

    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "No data")
        }
    }
    if (state.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}