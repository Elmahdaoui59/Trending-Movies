package com.example.trendingmovies.presentation.moviedetail

sealed class MovieDetailEvent {
    class RequestMovieDetail(val movieId: Int): MovieDetailEvent()
}