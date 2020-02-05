package com.sunfusheng.github.kotlin.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sunfusheng.github.kotlin.R
import com.sunfusheng.github.kotlin.util.StatusBarUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setTranslucent(this)
        setContentView(R.layout.activity_main)

        initToolbar()
        initTabs()

//        startActivity(Intent(this, LoginActivity::class.java))
//        finish()
    }

    private fun initToolbar() {
        val layoutParams = vStatusBar.layoutParams
        layoutParams.height = StatusBarUtil.getStatusBarHeight(this)
        vStatusBar.layoutParams = layoutParams
        vToolbar.title = getString(R.string.app_name)
    }

    private fun initTabs() {
        vHome.setOnClickListener {
            vHome.setTabSelected()
            vDiscover.setTabNormal()
            vMe.setTabNormal()
        }
        vDiscover.setOnClickListener {
            vHome.setTabNormal()
            vDiscover.setTabSelected()
            vMe.setTabNormal()
        }
        vMe.setOnClickListener {
            vHome.setTabNormal()
            vDiscover.setTabNormal()
            vMe.setTabSelected()
        }
    }

}
