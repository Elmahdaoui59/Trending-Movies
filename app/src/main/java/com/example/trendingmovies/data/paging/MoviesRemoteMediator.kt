package com.example.trendingmovies.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.trendingmovies.data.local.MoviesDatabase
import com.example.trendingmovies.data.remote.MoviesApi
import com.example.trendingmovies.domain.model.Movie
import com.example.trendingmovies.domain.model.MoviesRemoteKeys
import com.example.trendingmovies.util.Constants
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator(
    private val moviesApi: MoviesApi,
    private val moviesDatabase: MoviesDatabase
) : RemoteMediator<Int, Movie>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Movie>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: Constants.STARTING_PAGE_NUMBER
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }

            LoadType.APPEND -> {
                val remoteKey = getRemoteKeyForLastItem(state)
                val nextKey = remoteKey?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
                nextKey

            }
        }
        try {
            val apiResponse = moviesApi.getMovies(page = page)
            val movies = apiResponse.movies
            val endOfPaginationReached = page == apiResponse.total_pages
            moviesDatabase.withTransaction {
                // if refresh, clear db
                if (loadType == LoadType.REFRESH) {
                    moviesDatabase.movieDao().clearMovies()
                    moviesDatabase.remoteKeysDao().clearRemoteKeys()
                }
                val prevKey = if (page > Constants.STARTING_PAGE_NUMBER) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1
                moviesDatabase.movieDao().insertMovies(movies)
                val insertedMovies = moviesDatabase.movieDao().getMoviesByIds(movies.map { it.id!! })
                val keys = insertedMovies.map {
                    MoviesRemoteKeys(it.key, prevKey, nextKey)
                }
                moviesDatabase.remoteKeysDao().insertKeys(keys)
            }
            return MediatorResult.Success(
                endOfPaginationReached = endOfPaginationReached
            )

        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Movie>): MoviesRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data
            ?.lastOrNull()
            ?.let { movie ->
                moviesDatabase.remoteKeysDao().getMovieRemoteKey(movie.key)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Movie>): MoviesRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data
            ?.firstOrNull()
            ?.let { movie ->
                moviesDatabase.remoteKeysDao().getMovieRemoteKey(movie.key)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Movie>
    ): MoviesRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.key?.let { movieKey ->
                moviesDatabase.remoteKeysDao().getMovieRemoteKey(movieKey)
            }
        }
    }

}