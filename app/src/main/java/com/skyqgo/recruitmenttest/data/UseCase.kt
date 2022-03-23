package com.skyqgo.recruitmenttest.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

interface UseCase<out O : Any> {

    val repository: MovieRepository

    @ExperimentalCoroutinesApi
    suspend fun execute(): Flow<DataState<O>>
}