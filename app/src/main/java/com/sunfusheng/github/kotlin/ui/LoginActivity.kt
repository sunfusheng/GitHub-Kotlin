package com.sunfusheng.github.kotlin.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import com.sunfusheng.github.kotlin.R
import com.sunfusheng.github.kotlin.ui.base.BaseActivity
import com.sunfusheng.github.kotlin.util.StatusBarUtil
import com.sunfusheng.mvvm.util.KeyboardUtil
import com.sunfusheng.mvvm.util.ToastUtil
import kotlinx.android.synthetic.main.activity_login.*

/**
 * @author sunfusheng
 * @since 2019-11-11
 */
class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setColor(this, resources.getColor(R.color.white), 0)
        setContentView(R.layout.activity_login)

        initView()
        initListeners()
    }

    private fun initView() {
        vLogo.postDelayed({
            vLogo.start()
        }, 200)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initListeners() {
        vRootView.setOnTouchListener { v, event ->
            v.clearFocus()
            KeyboardUtil.hideKeyboard(v)
            true
        }

        vLogo.setOnClickListener {
            vLogo.start()
        }

        vPassword.setCommittedCallback(Runnable {
            login()
        })

        vLogin.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val username = vUsername.getUsername()
        if (TextUtils.isEmpty(username)) {
            ToastUtil.show(R.string.username_input)
            return
        }

        val password = vPassword.getPassword()
        if (TextUtils.isEmpty(password)) {
            ToastUtil.show(R.string.password_input)
            return
        }

        vRootView.clearFocus()
        KeyboardUtil.hideKeyboard(vRootView)
    }
}