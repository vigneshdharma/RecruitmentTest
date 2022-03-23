package com.skyqgo.recruitmenttest.domain

import com.skyqgo.recruitmenttest.data.DataState
import com.skyqgo.recruitmenttest.data.MovieRepository
import com.skyqgo.recruitmenttest.data.UseCase
import com.skyqgo.recruitmenttest.data.model.MovieListResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetMoviesUseCase @Inject constructor(override val repository: MovieRepository) :
    UseCase<MovieListResponse> {

    @ExperimentalCoroutinesApi
    override suspend fun execute(): Flow<DataState<MovieListResponse>> {
        return repository.getMovies()
    }
}