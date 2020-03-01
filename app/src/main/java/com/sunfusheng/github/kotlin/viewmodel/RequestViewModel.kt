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
    open val loadingState = MutableLiveData<Boolean>()
    open val requestState = MutableLiveData<Result<*>>()
    open val exceptionState = MutableLiveData<Exception>()

    private fun <Response> requestInternal(apiDSL: RequestDSL<Response>.() -> Unit) {
        RequestDSL<Response>().apply(apiDSL).launch(viewModelScope)
    }

    protected fun <Response> request(requestDSL: RequestDSL<Response>.() -> Unit) {
        requestInternal<Response> {
            val invoker = RequestDSL<Response>().apply(requestDSL)
            onStart {
                loadingState.value = true
                invoker.onStart?.invoke()
                requestState.value = Result.Start<Response>()
            }
            onRequest {
                invoker.onRequest()
            }
            onResponse { response ->
                loadingState.value = false
                invoker.onResponse?.invoke(response)
                requestState.value = Result.Response(response)
            }
            onError { exception ->
                loadingState.value = false
                invoker.onError?.invoke(exception)
                requestState.value = Result.Error<Response>(exception)
                exceptionState.value = exception
            }
            onFinally {
                if (loadingState.value != null && loadingState.value == true) {
                    loadingState.value = false
                }
                invoker.onFinally?.invoke()
                requestState.value = Result.Finally<Response>()
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