package com.sunfusheng.github.kotlin.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

/**
 * @author sunfusheng
 * @since 2020/2/29
 */
open class RequestViewModel : ViewModel() {
    open val loadingLiveData = MutableLiveData<Boolean>()
    open val exceptionLiveData = MutableLiveData<Exception>()

    private fun <Response> requestInternal(apiDSL: RequestDSL<Response>.() -> Unit) {
        RequestDSL<Response>().apply(apiDSL).launch(viewModelScope)
    }

    protected fun <Response> requestDSL(requestDSL: RequestDSL<Response>.() -> Unit) {
        requestInternal<Response> {
            onStart {
                loadingLiveData.value = true
                RequestDSL<Response>().apply(requestDSL).onStart?.invoke()
            }

            onRequest {
                RequestDSL<Response>().apply(requestDSL).onRequest()
            }

            onResponse { response ->
                RequestDSL<Response>().apply(requestDSL).onResponse?.invoke(response)
            }

            onError { exception ->
                exceptionLiveData.value = exception
                RequestDSL<Response>().apply(requestDSL).onError?.invoke(exception)
            }

            onFinally {
                loadingLiveData.value = false
                RequestDSL<Response>().apply(requestDSL).onFinally?.invoke()
            }
        }
    }

    protected fun <Response> requestLiveData(
        context: CoroutineContext = Dispatchers.Main,
        timeoutInMs: Long = 5000L,
        request: suspend () -> Response
    ): LiveData<Result<Response>> {
        return liveData(context, timeoutInMs) {
            emit(Result.Start())
            try {
                emit(withContext(Dispatchers.IO) {
                    Result.Response(request())
                })
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(e))
            } finally {
                emit(Result.Finally())
            }
        }
    }
}

sealed class Result<T> {
    class Start<T> : Result<T>()
    data class Response<T>(val response: T) : Result<T>()
    data class Error<T>(val exception: Exception) : Result<T>()
    class Finally<T> : Result<T>()
}