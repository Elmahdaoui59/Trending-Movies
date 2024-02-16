package com.example.trendingmovies.presentation.common

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
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
        modifier = modifier,
        alignment = Alignment.CenterStart
    )
}