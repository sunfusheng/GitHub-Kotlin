package com.sunfusheng.github.kotlin

import android.util.Log
import javax.inject.Inject

/**
 * @author by sunfusheng on 2019/1/28
 */
class Repo @Inject constructor(var repoName: String, val user: User) {

    fun info() {
        Log.d("--->", "$repoName owned by ${user.userName}")
    }
}