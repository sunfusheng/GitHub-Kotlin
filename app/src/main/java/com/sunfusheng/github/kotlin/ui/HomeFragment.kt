package com.sunfusheng.github.kotlin.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import com.sunfusheng.github.kotlin.R
import com.sunfusheng.github.kotlin.ui.base.BaseFragment
import com.sunfusheng.github.kotlin.viewmodel.BaseViewModel
import com.sunfusheng.github.kotlin.viewmodel.OnError
import com.sunfusheng.github.kotlin.viewmodel.OnResponse
import com.sunfusheng.github.kotlin.viewmodel.getViewModel
import kotlinx.android.synthetic.main.fragment_todo.*
import kotlinx.coroutines.delay

/**
 * @author sunfusheng
 * @since 2020-02-05
 */
class HomeFragment : BaseFragment() {

    override fun initData(arguments: Bundle?) {
        val vm = getViewModel(VM::class.java)
        vm.loadData1()
        vm.loadData2()

        vm.requestState(TAG1)?.observe(this, Observer {
            when (it) {
                is OnResponse<*> -> {
                    Log.w("sfs", "requestState(tag=$TAG1), OnResponse: ${it.response}")
                }
                is OnError -> {
                    Log.e("sfs", "requestState(tag=$TAG1), OnError: ${it.exception}")
                }
            }
        })

        vm.requestState(TAG2)?.observe(this, Observer {
            when (it) {
                is OnResponse<*> -> {
                    Log.w("sfs", "requestState(tag=$TAG2), OnResponse: ${it.response}")
                }
                is OnError -> {
                    Log.e("sfs", "requestState(tag=$TAG2), OnError: ${it.exception}")
                }
            }
        })
    }

    override fun inflateLayout(): Int {
        return R.layout.fragment_todo
    }

    override fun initView(rootView: View) {
        vTip.text = "Home"
    }
}

const val TAG1 = "loadData1"
const val TAG2 = "loadData2"

class VM : BaseViewModel() {
    fun loadData1() {
        request<Int>(jobTag = TAG1) {
            onStart {
                Log.d("sfs", "loadData1() onStart")
            }
            onRequest {
                Log.d("sfs", "loadData1() onRequest")
                delay(3000)
                3000
            }
            onResponse {
                Log.w("sfs", "loadData1() onResponse: $it")
            }
            onError {
                Log.e("sfs", "loadData1() onError: $it")
            }
            onFinally {
                Log.d("sfs", "loadData1() onFinally")
            }
        }
    }

    fun loadData2() {
        request<Int>(jobTag = TAG2) {
            onStart {
                Log.d("sfs", "loadData2() onStart")
            }
            onRequest {
                Log.d("sfs", "loadData2() onRequest")
                delay(5000)
                5000
                throw RuntimeException("Oh, my god!")
            }
            onResponse {
                Log.w("sfs", "loadData2() onResponse: $it")
            }
            onError {
                Log.e("sfs", "loadData2() onError: $it")
            }
            onFinally {
                Log.d("sfs", "loadData2() onFinally")
            }
        }
    }
}