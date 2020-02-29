package com.sunfusheng.github.kotlin.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sunfusheng.github.kotlin.R
import com.sunfusheng.github.kotlin.ui.base.BaseFragment
import com.sunfusheng.github.kotlin.viewmodel.BaseViewModel
import com.sunfusheng.github.kotlin.viewmodel.Result
import kotlinx.android.synthetic.main.fragment_todo.*
import kotlinx.coroutines.delay

/**
 * @author sunfusheng
 * @since 2020-02-05
 */
class HomeFragment : BaseFragment() {

    class TestViewModel : BaseViewModel() {

        fun loadDSL() {
            requestDSL<Int> {
                onRequest {
                    delay(3000)
                    3000
                }

                onResponse {
                }
            }
        }

        fun loadLiveData(): LiveData<Result<Int>> {
            return requestLiveData {
                delay(3000)
                throw RuntimeException("O, My god!")
                3000
            }
        }
    }

    override fun initData(arguments: Bundle?) {
        val vm = ViewModelProvider(this).get(TestViewModel::class.java)
        vm.loadDSL()

        vm.loadLiveData().observe(this, Observer {
            Log.w("sfs", "observe() $it")

        })
    }

    override fun inflateLayout(): Int {
        return R.layout.fragment_todo
    }

    override fun initView(rootView: View) {
        vTip.text = "Home"
    }
}