package com.sunfusheng.github.kotlin

import android.util.Log

/**
 * @author by sunfusheng on 2019/1/28
 */
data class User constructor(val userName: String) {

    fun info() {
        Log.d("--->", userName)
    }
}