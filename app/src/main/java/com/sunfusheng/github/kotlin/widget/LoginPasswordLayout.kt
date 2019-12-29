package com.sunfusheng.github.kotlin.widget

import android.content.Context
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.sunfusheng.github.kotlin.R


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
    private var vEditText: EditText
    private var vSuffixIcon: ImageView

    private var showPassword = false

    init {
        val rootView =
            LayoutInflater.from(getContext()).inflate(R.layout.layout_login_edittext, this)
        vEditText = rootView.findViewById(R.id.vEditText)
        vPrefixIcon = rootView.findViewById(R.id.vPrefixIcon)
        vSuffixIcon = rootView.findViewById(R.id.vSuffixIcon)

        vPrefixIcon.setBackgroundResource(R.mipmap.ic_action_password)
        vEditText.setHint(R.string.password_input)
        vEditText.setTransformationMethod(PasswordTransformationMethod.getInstance())
        vSuffixIcon.setBackgroundResource(R.mipmap.ic_action_eye_close)

        vEditText.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                vPrefixIcon.setBackgroundResource(R.mipmap.ic_action_password_selected)
            } else {
                vPrefixIcon.setBackgroundResource(R.mipmap.ic_action_password)
            }
        }

        vSuffixIcon.setOnClickListener {
            showPassword = !showPassword
            if (showPassword) {
                vSuffixIcon.setBackgroundResource(R.mipmap.ic_action_eye_open)
                vEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance())
            } else {
                vSuffixIcon.setBackgroundResource(R.mipmap.ic_action_eye_close)
                vEditText.setTransformationMethod(PasswordTransformationMethod.getInstance())
            }
            vEditText.setSelection(getPassword().length)
        }
    }

    fun getPassword(): String {
        return vEditText.text.toString()
    }
}