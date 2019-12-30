package com.sunfusheng.github.kotlin.util

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.annotation.StringRes

/**
 * @author sunfusheng
 * @since 2019-12-30
 */
object ToastUtil {
    private var mToast: Toast? = null

    fun toast(@StringRes resId: Int) {
        toast(AppUtil.getApp().getString(resId), true)
    }

    fun toast(msg: String) {
        toast(msg, true)
    }

    fun toastLong(@StringRes resId: Int) {
        toast(AppUtil.getApp().getString(resId), false)
    }

    fun toastLong(msg: String) {
        toast(msg, false)
    }

    @SuppressLint("ShowToast")
    fun toast(msg: String?, isShort: Boolean) {
        if (AppUtil.isAppForeground() && msg?.isNotEmpty() == true) {
            var duration = if (isShort) Toast.LENGTH_SHORT else Toast.LENGTH_LONG
            if (msg.length > 20) {
                duration = Toast.LENGTH_LONG
            }

            if (mToast == null) {
                mToast = Toast.makeText(AppUtil.getApp(), msg, duration)
            }
            mToast!!.setText(msg)
            mToast!!.duration = duration
            mToast!!.show()
        }
    }
}