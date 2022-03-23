package com.skyqgo.recruitmenttest.data

import android.content.Context
import android.widget.Toast
import androidx.annotation.NonNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retryWhen
import retrofit2.Response
import java.io.IOException

typealias NetworkAPIInvoke<T> = suspend () -> Response<T>

sealed class DataState<out T : Any> {
    data class OnSuccess<out T : Any>(val data: T) : DataState<T>()
    data class OnFailed(val throwable: Throwable) : DataState<Nothing>()
}

@ExperimentalCoroutinesApi
suspend fun <T : Any> performSafeNetworkApiCall(
    messageInCaseOfError: String = "Network error",
    allowRetries: Boolean = true,
    numberOfRetries: Int = 2,
    networkApiCall: NetworkAPIInvoke<T>
): Flow<DataState<T>> {
    var delayDuration = 1000L
    val delayFactor = 2
    return flow {
        val response = networkApiCall()
        if (response.isSuccessful) {
            response.body()?.let {
                emit(DataState.OnSuccess(it))
            }
                ?: emit(DataState.OnFailed(IOException("API call successful but empty response body")))
            return@flow
        }
        emit(
            DataState.OnFailed(
                IOException(
                    "API call failed with error - ${
                        response.errorBody()
                            ?.string() ?: messageInCaseOfError
                    }"
                )
            )
        )
        return@flow
    }.catch { e ->
        emit(DataState.OnFailed(IOException("Exception during network API call: ${e.message}")))
        return@catch
    }.retryWhen { cause, attempt ->
        if (!allowRetries || attempt > numberOfRetries || cause !is IOException) return@retryWhen false
        delay(delayDuration)
        delayDuration *= delayFactor
        return@retryWhen true
    }.flowOn(Dispatchers.IO)
}

sealed class ViewState<out T : Any> {
    data class Loading(val isLoading: Boolean) : ViewState<Nothing>()
    data class RenderSuccess<out T : Any>(val output: T) : ViewState<T>()
    class RenderFailure(val throwable: Throwable) : ViewState<Nothing>()
}

fun Context.showToast(@NonNull message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

@ExperimentalCoroutinesApi
suspend fun <T : Any> getViewStateFlowForNetworkCall(ioOperation: suspend () -> Flow<DataState<T>>) =
    flow {
        emit(ViewState.Loading(true))
        ioOperation().map {
            when (it) {
                is DataState.OnSuccess -> ViewState.RenderSuccess(it.data)
                is DataState.OnFailed -> ViewState.RenderFailure(it.throwable)
            }
        }.collect {
            emit(it)
        }
        emit(ViewState.Loading(false))
    }.flowOn(Dispatchers.IO)
