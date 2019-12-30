package com.sunfusheng.github.kotlin.widget

import android.content.Context
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.sunfusheng.github.kotlin.R
import io.reactivex.functions.Action


/**
 * @author sunfusheng
 * @since 2019-12-29
 */
class LoginPasswordLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var vPrefixIcon: ImageView
    private var vContent: EditText
    private var vSuffixIcon: ImageView

    private var showPassword = false
    private var mCommittedCallback: Action? = null

    init {
        val rootView =
            LayoutInflater.from(getContext()).inflate(R.layout.layout_login_edittext, this)
        vContent = rootView.findViewById(R.id.vContent)
        vPrefixIcon = rootView.findViewById(R.id.vPrefixIcon)
        vSuffixIcon = rootView.findViewById(R.id.vSuffixIcon)

        vPrefixIcon.setBackgroundResource(R.drawable.ic_lock_black)
        vContent.setHint(R.string.password_input)
        vContent.setTransformationMethod(PasswordTransformationMethod.getInstance())
        vSuffixIcon.setBackgroundResource(R.drawable.ic_visibility_off_black)

        vContent.setImeOptions(EditorInfo.IME_ACTION_SEND)
        vContent.setOnEditorActionListener { v: TextView?, actionId: Int, event: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                mCommittedCallback?.run()
                true
            } else {
                false
            }
        }

        vContent.setOnFocusChangeListener { v: View?, hasFocus: Boolean ->
            if (hasFocus) {
                vPrefixIcon.setBackgroundResource(R.drawable.ic_lock_red)
            } else {
                vPrefixIcon.setBackgroundResource(R.drawable.ic_lock_black)
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

    fun setCommittedCallback(callback: Action) {
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