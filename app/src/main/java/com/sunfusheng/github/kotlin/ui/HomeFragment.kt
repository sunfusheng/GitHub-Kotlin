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

    }

    override fun inflateLayout() = R.layout.fragment_todo

    override fun initView(rootView: View) {
        vTip.text = "Home"
    }
}