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
            val invoker = RequestDSL<Response>().apply(requestDSL)
            onStart {
                loadingLiveData.value = true
                invoker.onStart?.invoke()
            }
            onRequest {
                invoker.onRequest()
            }
            onResponse { response ->
                loadingLiveData.value = false
                invoker.onResponse?.invoke(response)
            }
            onError { exception ->
                loadingLiveData.value = false
                exceptionLiveData.value = exception
                invoker.onError?.invoke(exception)
            }
            onFinally {
                if (loadingLiveData.value != null && loadingLiveData.value == true) {
                    loadingLiveData.value = false
                }
                invoker.onFinally?.invoke()
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