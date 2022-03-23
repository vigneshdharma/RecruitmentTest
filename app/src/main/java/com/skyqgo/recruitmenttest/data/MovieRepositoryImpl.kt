package com.skyqgo.recruitmenttest.data

import com.skyqgo.recruitmenttest.data.model.MovieListResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(override val remoteDataSource: RemoteDataSource) :
    MovieRepository {

    @ExperimentalCoroutinesApi
    override suspend fun getMovies(): Flow<DataState<MovieListResponse>> =
        remoteDataSource.getMovies()
}