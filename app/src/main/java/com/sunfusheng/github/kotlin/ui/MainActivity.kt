package com.sunfusheng.github.kotlin.ui

import android.os.Bundle
import android.util.SparseArray
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sunfusheng.github.kotlin.R
import com.sunfusheng.github.kotlin.ui.base.FragmentViewPager2Adapter
import com.sunfusheng.github.kotlin.util.StatusBarUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class MainActivity : AppCompatActivity() {

    private val TAB_HOME = 0
    private val TAB_DISCOVER = 1
    private val TAB_ME = 2

    private var mSelectedTab = TAB_HOME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setTranslucent(this)
        setContentView(R.layout.activity_main)

        initToolbar()
        initTabs()
        switchTab(mSelectedTab)
        initViewPager2()

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
            switchTab(TAB_HOME)
        }
        vDiscover.setOnClickListener {
            switchTab(TAB_DISCOVER)
        }
        vMe.setOnClickListener {
            switchTab(TAB_ME)
        }
    }

    private fun switchTab(position: Int) {
        when (position) {
            TAB_HOME -> {
                vHome.setTabSelected()
                vDiscover.setTabNormal()
                vMe.setTabNormal()
                viewPager2.setCurrentItem(TAB_HOME, false)
            }
            TAB_DISCOVER -> {
                vHome.setTabNormal()
                vDiscover.setTabSelected()
                vMe.setTabNormal()
                viewPager2.setCurrentItem(TAB_DISCOVER, false)
            }
            TAB_ME -> {
                vHome.setTabNormal()
                vDiscover.setTabNormal()
                vMe.setTabSelected()
                viewPager2.setCurrentItem(TAB_ME, false)
            }
        }
    }

    private fun initViewPager2() {
        val fragments = SparseArray<Fragment>()
        fragments.put(TAB_HOME, HomeFragment())
        fragments.put(TAB_DISCOVER, DiscoverFragment())
        fragments.put(TAB_ME, UserFragment())

        val adapter = FragmentViewPager2Adapter(this)
        adapter.setFragments(fragments)
        viewPager2.isUserInputEnabled = false
        viewPager2.adapter = adapter
    }

}
