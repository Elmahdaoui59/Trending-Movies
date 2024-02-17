package com.example.trendingmovies.presentation.movies

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.trendingmovies.BuildConfig
import com.example.trendingmovies.domain.model.Movie
import com.example.trendingmovies.presentation.common.MoviePoster


@Composable
fun MovieItemLayout(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        val moviePosterUrl  by remember(movie.poster_path) {
            mutableStateOf(BuildConfig.POSTER_BASE_URL + movie.poster_path)
        }
        //Log.e("url", moviePosterUrl)
        MoviePoster(
            url = moviePosterUrl,
            modifier = Modifier.size(80.dp)
        )
        Column(
            horizontalAlignment = Alignment.Start,
        ) {
            movie.title?.let { Text(text = it, fontWeight = FontWeight.Bold) }
            Spacer(modifier = Modifier.height(5.dp))
            movie.release_date?.let { Text(text = "Release Date: $it") }
        }
    }
}