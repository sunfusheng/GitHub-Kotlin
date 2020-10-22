package com.sunfusheng.mvvm.util

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**
 * @author sunfusheng
 * @since  2020/10/22
 */
class AppLifecycleObserver : DefaultLifecycleObserver {

    var isForeground = false

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        isForeground = true
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        isForeground = false
    }
}