package com.sunfusheng.mvvm.util

import android.util.Log

/**
 * @author sunfusheng
 * @since  2020/10/22
 */
object LogUtil {
    private const val TAG = "GitHub"
    private const val SUB_TAG = "[sfs]"

    fun d(text: String?) {
        Log.d(TAG, "$SUB_TAG $text")
    }

    fun w(text: String?) {
        Log.w(TAG, "$SUB_TAG $text")
    }

    fun e(text: String?) {
        Log.e(TAG, "$SUB_TAG $text")
    }
}