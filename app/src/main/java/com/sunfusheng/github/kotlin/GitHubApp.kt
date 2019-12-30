package com.sunfusheng.github.kotlin

import android.app.Application
import com.sunfusheng.github.kotlin.util.AppUtil

/**
 * @author sunfusheng
 * @since 2019-12-30
 */
class GitHubApp : Application() {

    override fun onCreate() {
        super.onCreate()

        AppUtil.init(this)
    }
}