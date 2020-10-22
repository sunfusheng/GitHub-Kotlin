package com.sunfusheng.mvvm.util

import android.view.View
import android.widget.EditText

/**
 * @author sunfusheng
 * @since 2019-12-30
 */
object KeyboardUtil {

    fun showKeyboard(editText: EditText) {
        editText.isCursorVisible = true
        editText.requestFocus()
        inputMethodManager.showSoftInput(editText, 0);
    }

    fun hideKeyboard(view: View) {
        inputMethodManager.hideSoftInputFromWindow(view.applicationWindowToken, 0);
    }
}