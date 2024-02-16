package com.example.trendingmovies.data.repository

import android.content.Context
import android.util.Log
import com.example.trendingmovies.BuildConfig
import com.example.trendingmovies.R
import com.example.trendingmovies.data.remote.MoviesApi
import com.example.trendingmovies.domain.model.MoviesResponse
import com.example.trendingmovies.domain.model.WebResponse
import com.example.trendingmovies.domain.model.moviedetail.MovieDetail
import com.example.trendingmovies.domain.repositories.MoviesRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.Exception

class MoviesRepositoryImpl(
    private val api: MoviesApi,
    private val context: Context
) : MoviesRepository {

    override fun getMovies(): Flow<WebResponse<MoviesResponse>> = flow {
        emit(WebResponse.Loading)
        try {
            if (BuildConfig.useMock) {
                val gson = Gson()
                val inputStream = context.resources.openRawResource(R.raw.moviesrsmock)
                val reader = BufferedReader(InputStreamReader(inputStream))
                val jsonResponse = reader.readLine()
                val response = gson.fromJson(jsonResponse, MoviesResponse::class.java)
                emit(WebResponse.Success(response))
                //Log.e("res", "Movies Response from mock: ${response.toString()}")
            } else {
                val response = api.getMovies()
                //Log.e("res", response.toString())
                emit(WebResponse.Success(response))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            // todo Emissions from 'catch' blocks are prohibited in order to avoid unspecified behaviour, 'Flow.catch' operator can be used instead.
            //     For a more detailed explanation, please refer to Flow documentation.
            emit(WebResponse.Failure(R.string.can_t_fetch_movies))
        }
    }.flowOn(Dispatchers.IO)

    override fun getMovieDetail(movieId: Int): Flow<WebResponse<MovieDetail>> = flow {
        emit(WebResponse.Loading)
        try {
            if (BuildConfig.useMock) {
                val gson = Gson()
                val inputStream = context.resources.openRawResource(R.raw.moviedetailmock)
                val reader = BufferedReader(InputStreamReader(inputStream))
                val jsonResponse = reader.readText()
                val response = gson.fromJson(jsonResponse, MovieDetail::class.java)
                emit(WebResponse.Success(response))
                //Log.e("res", "Movie Detail Response from mock: ${response.toString()}")
            } else {
                val result = api.getMovieDetail(movieId)
                emit(WebResponse.Success(result))
                //Log.e("detail", result.toString())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(WebResponse.Failure(R.string.can_t_fetch_movie_detail))
        }
    }


}