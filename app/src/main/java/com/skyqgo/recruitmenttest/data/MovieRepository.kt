package com.skyqgo.recruitmenttest.data

import com.skyqgo.recruitmenttest.data.model.MovieListResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    val remoteDataSource: RemoteDataSource

    @ExperimentalCoroutinesApi
    suspend fun getMovies(): Flow<DataState<MovieListResponse>>
}