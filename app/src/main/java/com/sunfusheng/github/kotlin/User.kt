package com.sunfusheng.github.kotlin

import android.util.Log
import dagger.Module
import javax.inject.Inject

/**
 * @author by sunfusheng on 2019/1/28
 */
@Module
class User(@Inject var userName: String) {

    fun info() {
        Log.d("--->", userName)
    }
}