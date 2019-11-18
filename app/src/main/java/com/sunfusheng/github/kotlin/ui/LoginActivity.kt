package com.sunfusheng.github.kotlin.ui

import android.os.Bundle
import com.sunfusheng.github.kotlin.R
import com.sunfusheng.github.kotlin.util.StatusBarUtil
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

        vLogo.setOnClickListener {
            vLogo.start()
        }

        vLogo.postDelayed({
            vLogo.start()
        }, 200)
    }
}