package com.example.trendingmovies.presentation.movies

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trendingmovies.BuildConfig
import com.example.trendingmovies.domain.model.Movie


@Composable
fun MovieItemLayout(
    movie: Movie
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        val moviePosterUrl = BuildConfig.POSTER_BASE_URL + movie.poster_path
        //Log.e("url", moviePosterUrl)
        MoviePoster(
            url = moviePosterUrl,
            modifier = Modifier.size(80.dp)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = movie.title)
            Text(text = movie.release_date)
        }
    }
}