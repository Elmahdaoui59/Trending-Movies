package com.example.trendingmovies.domain.model

sealed class WebResponse<out T> {
    object Loading : WebResponse<Nothing>()
    data class Success<out T>(
        val data: T
    ) : WebResponse<T>()

    data class Failure(
        val messageId: Int
    ) : WebResponse<Nothing>()
}