package com.example.trendingmovies.data.repository

import android.content.Context
import android.util.Log
import com.example.trendingmovies.BuildConfig
import com.example.trendingmovies.R
import com.example.trendingmovies.data.remote.MoviesApi
import com.example.trendingmovies.domain.model.MoviesResponse
import com.example.trendingmovies.domain.model.WebResponse
import com.example.trendingmovies.domain.repositories.MoviesRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.security.PrivateKey
import javax.inject.Inject

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
                Log.e("res", "Response from mock: ${response.toString()}")
            } else {
                val response = api.getMovies()
                Log.e("res", response.toString())
                emit(WebResponse.Success(response))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            // todo Emissions from 'catch' blocks are prohibited in order to avoid unspecified behaviour, 'Flow.catch' operator can be used instead.
            //     For a more detailed explanation, please refer to Flow documentation.
            emit(WebResponse.Failure(R.string.can_t_fetch_movies))
        }
    }.flowOn(Dispatchers.IO)


}