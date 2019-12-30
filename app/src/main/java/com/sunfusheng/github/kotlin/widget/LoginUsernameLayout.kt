package com.sunfusheng.github.kotlin.widget

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import com.sunfusheng.github.kotlin.R

/**
 * @author sunfusheng
 * @since 2019-12-29
 */
class LoginUsernameLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var vPrefixIcon: ImageView
    private var vContent: EditText
    private var vSuffixIcon: ImageView

    init {
        val rootView =
            LayoutInflater.from(getContext()).inflate(R.layout.layout_login_edittext, this)
        vContent = rootView.findViewById(R.id.vContent)
        vPrefixIcon = rootView.findViewById(R.id.vPrefixIcon)
        vSuffixIcon = rootView.findViewById(R.id.vSuffixIcon)

        vPrefixIcon.setBackgroundResource(R.drawable.ic_person_black)
        vContent.setHint(R.string.username_input)
        vSuffixIcon.visibility = View.INVISIBLE
        vSuffixIcon.setBackgroundResource(R.drawable.ic_clear_black)

        vContent.setOnFocusChangeListener { v: View, hasFocus: Boolean ->
            if (hasFocus) {
                vPrefixIcon.setBackgroundResource(R.drawable.ic_person_red)
            } else {
                vPrefixIcon.setBackgroundResource(R.drawable.ic_person_black)
            }
        }

        vContent.addTextChangedListener(afterTextChanged = { text: Editable? ->
            if (text?.isEmpty() == false) {
                vSuffixIcon.visibility = View.VISIBLE
            } else {
                vSuffixIcon.visibility = View.INVISIBLE
            }
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