package com.sunfusheng.github.kotlin.widget

import android.content.Context
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
    private var vEditText: EditText
    private var vSuffixIcon: ImageView

    init {
        val rootView =
            LayoutInflater.from(getContext()).inflate(R.layout.layout_login_edittext, this)
        vEditText = rootView.findViewById(R.id.vEditText)
        vPrefixIcon = rootView.findViewById(R.id.vPrefixIcon)
        vSuffixIcon = rootView.findViewById(R.id.vSuffixIcon)

        vPrefixIcon.setBackgroundResource(R.mipmap.ic_action_person)
        vEditText.setHint(R.string.username_input)
        vSuffixIcon.visibility = View.INVISIBLE
        vSuffixIcon.setBackgroundResource(R.mipmap.ic_action_clear)

        vEditText.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                vPrefixIcon.setBackgroundResource(R.mipmap.ic_action_person_selected)
            } else {
                vPrefixIcon.setBackgroundResource(R.mipmap.ic_action_person)
            }
        }

        vEditText.addTextChangedListener(afterTextChanged = {
            if (it?.isEmpty() == false) {
                vSuffixIcon.visibility = View.VISIBLE
            } else {
                vSuffixIcon.visibility = View.INVISIBLE
            }
        })

        vSuffixIcon.setOnClickListener {
            vEditText.setText("")
        }
    }

    fun getUsername(): String {
        return vEditText.text.toString()
    }
}