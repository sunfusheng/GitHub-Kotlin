package com.sunfusheng.github.kotlin.widget.app

import android.content.Context
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.sunfusheng.github.kotlin.R
import kotlinx.android.synthetic.main.layout_login_edittext.view.*


/**
 * @author sunfusheng
 * @since 2019-12-29
 */
class LoginPasswordView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var showPassword = false
    private var mCommittedCallback: Runnable? = null

    init {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_login_edittext, this)

        vPrefixIcon.setBackgroundResource(R.drawable.ic_lock_black)
        vContent.setHint(R.string.password_input)
        vContent.setTransformationMethod(PasswordTransformationMethod.getInstance())
        vSuffixIcon.setBackgroundResource(R.drawable.ic_visibility_off_black)

        vContent.setOnFocusChangeListener { v: View?, hasFocus: Boolean ->
            vPrefixIcon.setBackgroundResource(if (hasFocus) R.drawable.ic_lock_red else R.drawable.ic_lock_black)
        }

        vContent.setImeOptions(EditorInfo.IME_ACTION_SEND)
        vContent.setOnEditorActionListener { v: TextView?, actionId: Int, event: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                mCommittedCallback?.run()
                true
            } else {
                false
            }
        }

        vSuffixIcon.setOnClickListener {
            showPassword = !showPassword
            if (showPassword) {
                vSuffixIcon.setBackgroundResource(R.drawable.ic_visibility_black)
                vContent.setTransformationMethod(HideReturnsTransformationMethod.getInstance())
            } else {
                vSuffixIcon.setBackgroundResource(R.drawable.ic_visibility_off_black)
                vContent.setTransformationMethod(PasswordTransformationMethod.getInstance())
            }
            vContent.setSelection(getPassword().length)
        }
    }

    fun getEditText(): EditText {
        return vContent
    }

    fun setCommittedCallback(callback: Runnable) {
        this.mCommittedCallback = callback
    }

    fun clearEditTextFocus() {
        if (vContent.hasFocus()) {
            vContent.clearFocus()
        }
    }

    fun getPassword(): String {
        return vContent.text.toString()
    }
}