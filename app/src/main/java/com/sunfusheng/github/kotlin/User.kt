package com.sunfusheng.github.kotlin

import android.util.Log
import javax.inject.Inject

/**
 * @author by sunfusheng on 2019/1/28
 */
class User @Inject constructor(val userName: String) {

    fun info() {
        Log.d("--->", userName)
    }
}