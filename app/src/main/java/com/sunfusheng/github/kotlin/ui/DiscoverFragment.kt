package com.sunfusheng.github.kotlin.ui

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import com.sunfusheng.github.kotlin.R
import com.sunfusheng.github.kotlin.ui.base.BaseFragment
import com.sunfusheng.github.kotlin.viewmodel.LifecycleHandler
import kotlinx.android.synthetic.main.fragment_todo.*

/**
 * @author sunfusheng
 * @since 2020-02-05
 */
class DiscoverFragment : BaseFragment() {

    override fun initData(arguments: Bundle?) {

    }

    override fun inflateLayout(): Int {
        return R.layout.fragment_todo
    }

    override fun initView(rootView: View) {
        vTip.text = "Discover"
    }

}