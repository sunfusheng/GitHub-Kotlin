package com.sunfusheng.github.kotlin.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.sunfusheng.github.kotlin.BundleKey
import com.trello.rxlifecycle2.components.support.RxFragment

/**
 * @author sunfusheng
 * @since 2020-02-05
 */
abstract class BaseFragment : RxFragment() {

    protected var mUsername: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mUsername = arguments?.getString(BundleKey.USERNAME)
        initData(arguments)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(inflateLayout(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    abstract fun initData(arguments: Bundle?)

    @LayoutRes
    abstract fun inflateLayout(): Int

    abstract fun initView(rootView: View)
}