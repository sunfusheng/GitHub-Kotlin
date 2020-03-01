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
    open val requestState = MutableLiveData<RequestState<*>>()
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
                requestState.value = OnStart<Response>()
            }
            onRequest {
                invoker.onRequest()
            }
            onResponse { response ->
                loadingState.value = false
                invoker.onResponse?.invoke(response)
                requestState.value = OnResponse(response)
            }
            onError { exception ->
                loadingState.value = false
                invoker.onError?.invoke(exception)
                requestState.value = OnError<Response>(exception)
                exceptionState.value = exception
            }
            onFinally {
                if (loadingState.value != null && loadingState.value == true) {
                    loadingState.value = false
                }
                invoker.onFinally?.invoke()
                requestState.value = OnFinally<Response>()
            }
        }
    }

    protected fun <Response> requestLiveData(
        context: CoroutineContext = Dispatchers.Main,
        timeoutInMs: Long = 5000L,
        request: suspend () -> Response
    ): LiveData<RequestState<Response>> {
        return liveData(context, timeoutInMs) {
            emit(OnStart())
            try {
                emit(withContext(Dispatchers.IO) {
                    OnResponse(request())
                })
            } catch (e: Exception) {
                e.printStackTrace()
                emit(OnError(e))
            } finally {
                emit(OnFinally())
            }
        }
    }
}

sealed class RequestState<T>
class OnStart<T> : RequestState<T>()
data class OnResponse<T>(val response: T) : RequestState<T>()
data class OnError<T>(val exception: Exception) : RequestState<T>()
class OnFinally<T> : RequestState<T>()