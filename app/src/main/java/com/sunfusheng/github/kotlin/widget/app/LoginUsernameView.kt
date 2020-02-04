package com.sunfusheng.github.kotlin.widget.app

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import com.sunfusheng.github.kotlin.R
import kotlinx.android.synthetic.main.layout_login_edittext.view.*

/**
 * @author sunfusheng
 * @since 2019-12-29
 */
class LoginUsernameView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_login_edittext, this)

        vPrefixIcon.setBackgroundResource(R.drawable.ic_person_black)
        vContent.setHint(R.string.username_input)
        vSuffixIcon.visibility = INVISIBLE
        vSuffixIcon.setBackgroundResource(R.drawable.ic_clear_black)

        vContent.setOnFocusChangeListener { v: View, hasFocus: Boolean ->
            vPrefixIcon.setBackgroundResource(if (hasFocus) R.drawable.ic_person_red else R.drawable.ic_person_black)
        }

        vContent.addTextChangedListener(afterTextChanged = { text: Editable? ->
            vSuffixIcon.visibility = if (text?.isEmpty() == true) INVISIBLE else VISIBLE
        })

        vSuffixIcon.setOnClickListener {
            vContent.setText("")
        }
    }

    fun getEditText(): EditText {
        return vContent
    }

    fun clearEditTextFocus() {
        if (vContent.hasFocus()) {
            vContent.clearFocus()
        }
    }

    fun getUsername(): String {
        return vContent.text.toString()
    }
}