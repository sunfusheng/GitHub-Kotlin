package com.sunfusheng.github.kotlin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sunfusheng.github.kotlin.ui.LoginActivity
import com.sunfusheng.github.kotlin.util.StatusBarUtil
import kotlinx.android.synthetic.main.layout_toolbar.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setTranslucent(this)
        setContentView(R.layout.activity_main)

        initToolbar()
        testFeatures()

        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun initToolbar() {
        val layoutParams = vStatusBar.layoutParams
        layoutParams.height = StatusBarUtil.getStatusBarHeight(this)
        vStatusBar.layoutParams = layoutParams
        vToolbar.title = getString(R.string.app_name)
    }

    @Throws(Exception::class)
    private fun testFeatures() {


    }

}
