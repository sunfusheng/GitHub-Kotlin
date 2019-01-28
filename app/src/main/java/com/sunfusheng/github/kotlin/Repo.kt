package com.sunfusheng.github.kotlin

import android.util.Log
import dagger.Module
import javax.inject.Inject

/**
 * @author by sunfusheng on 2019/1/28
 */
@Module
class Repo (@Inject var repoName: String, val user: User) {

    fun info() {
        Log.d("--->", "$repoName owned by ${user.userName}")
    }
}