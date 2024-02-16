package com.example.trendingmovies.presentation.movies

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ErrorResult
import coil.request.ImageRequest
import com.example.trendingmovies.R


@Composable
fun MoviePoster(
    url: String,
    modifier: Modifier
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest
            .Builder(LocalContext.current)
            .data(data = url).apply(block = fun ImageRequest.Builder.() {
                error(R.drawable.baseline_broken_image_24)
                placeholder(R.drawable.baseline_image_24)
            }
            ).build()
    )
    Image(
        painter = painter,
        contentDescription = "movie poster",
        modifier = modifier
    )
}