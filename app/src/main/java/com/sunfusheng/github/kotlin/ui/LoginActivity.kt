package com.sunfusheng.github.kotlin.ui

import android.os.Bundle
import android.text.TextUtils
import com.sunfusheng.github.kotlin.R
import com.sunfusheng.github.kotlin.util.KeyboardUtil
import com.sunfusheng.github.kotlin.util.StatusBarUtil
import com.sunfusheng.github.kotlin.util.ToastUtil
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
        val password = vPassword.getPassword()
        if (TextUtils.isEmpty(username)) {
            ToastUtil.toast(R.string.username_input)
            return
        }

        if (TextUtils.isEmpty(password)) {
            ToastUtil.toast(R.string.password_input)
            return
        }

        vRootView.clearFocus()
        KeyboardUtil.hideKeyboard(vRootView)
    }
}