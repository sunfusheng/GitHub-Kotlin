package com.sunfusheng.mvvm.util

import android.app.ActivityManager
import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import android.view.inputmethod.InputMethodManager

/**
 * @author sunfusheng
 * @since 2020/4/4
 */
val resources: Resources by lazy { ContextHolder.context.resources }

val assets: AssetManager by lazy { ContextHolder.context.assets }

val packageManager by lazy { ContextHolder.context.packageManager }

val activityManager by lazy {
    ContextHolder.context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
}

val inputMethodManager by lazy {
    ContextHolder.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
}