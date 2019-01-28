package com.sunfusheng.github.kotlin

import android.util.Log

/**
 * @author by sunfusheng on 2019/1/28
 */
data class Repo constructor(val repoName: String, val user: User) {

    fun info() {
        Log.d("--->", "$repoName owned by ${user.userName}")
    }
}