package com.sunfusheng.mvvm.util

import android.widget.Toast
import androidx.annotation.StringRes

/**
 * @author sunfusheng
 * @since 2020/4/5
 */
object ToastUtil {

    fun show(text: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(ContextHolder.context, text, duration).show()
    }

    fun show(@StringRes text: Int, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(ContextHolder.context, text, duration).show()
    }
}