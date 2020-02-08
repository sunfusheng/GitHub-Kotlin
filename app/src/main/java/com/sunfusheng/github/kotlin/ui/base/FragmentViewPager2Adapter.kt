package com.sunfusheng.github.kotlin.ui.base

import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * @author sunfusheng
 * @since 2020-02-08
 */
class FragmentViewPager2Adapter : FragmentStateAdapter {
    private var fragments: SparseArray<Fragment>? = null

    constructor(fragmentActivity: FragmentActivity) : super(fragmentActivity) {}
    constructor(fragment: Fragment) : super(fragment) {}
    constructor(
        fragmentManager: FragmentManager,
        lifecycle: Lifecycle
    ) : super(fragmentManager, lifecycle) {
    }

    fun setFragments(fragments: SparseArray<Fragment>?) {
        this.fragments = fragments
    }

    fun getFragments(): SparseArray<Fragment>? {
        return fragments
    }

    fun getFragment(position: Int): Fragment {
        return fragments!![position]
    }

    override fun createFragment(position: Int): Fragment {
        return fragments!![position]
    }

    override fun getItemCount(): Int {
        return fragments!!.size()
    }
}