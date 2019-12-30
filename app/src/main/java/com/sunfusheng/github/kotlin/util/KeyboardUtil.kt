package com.sunfusheng.github.kotlin.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * @author sunfusheng
 * @since 2019-12-30
 */
object KeyboardUtil {
    val sInputMethodManager: InputMethodManager by lazy {
        AppUtil.getApp().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    // 显示键盘
    fun showKeyboard(editText: EditText) {
        editText.isCursorVisible = true
        editText.requestFocus()
        sInputMethodManager.showSoftInput(editText, 0);
    }

    // 隐藏键盘
    fun hideKeyboard(v: View) {
        sInputMethodManager.hideSoftInputFromWindow(v.applicationWindowToken, 0);
    }
}