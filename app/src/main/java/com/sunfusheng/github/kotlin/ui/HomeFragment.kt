package com.sunfusheng.github.kotlin.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.sunfusheng.github.kotlin.R
import com.sunfusheng.github.kotlin.ui.base.BaseFragment
import com.sunfusheng.github.kotlin.viewmodel.BaseViewModel
import com.sunfusheng.github.kotlin.viewmodel.OnResponse
import com.sunfusheng.github.kotlin.viewmodel.RequestState
import com.sunfusheng.github.kotlin.viewmodel.getViewModel
import kotlinx.android.synthetic.main.fragment_todo.*

/**
 * @author sunfusheng
 * @since 2020-02-05
 */
class HomeFragment : BaseFragment() {

    class VM : BaseViewModel() {
        fun loadData(): LiveData<RequestState<String>> {
            return requestLiveData {
                ""
            }
        }
    }

    override fun initData(arguments: Bundle?) {
        val vm = getViewModel(VM::class.java)


        vm.requestState.observe(this, Observer {
            when (it) {
                is OnResponse -> {
                    val res = it.response
                    Log.d("sfs", "ass $res")
                }
            }
        })

        vm.loadData().observe(this, Observer {
            when (it) {
                is OnResponse -> {
                    val res = it.response
                    Log.d("sfs", "ass $res")
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