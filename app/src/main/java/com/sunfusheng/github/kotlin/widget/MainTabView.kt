package com.sunfusheng.github.kotlin.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.sunfusheng.github.kotlin.R
import kotlinx.android.synthetic.main.item_main_tab.view.*

/**
 * @author sunfusheng
 * @since 2020-02-04
 */
class MainTabView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var mDrawable: Drawable? = null
    private var mColorNormal: Int = 0x999999
    private var mColorSelected: Int = 0xC34A42

    private var isTabSelected: Boolean = false

    init {
        LayoutInflater.from(getContext()).inflate(R.layout.item_main_tab, this)

        attrs?.let {
            context.obtainStyledAttributes(attrs, R.styleable.MainTabView).apply {
                mDrawable = getDrawable(R.styleable.MainTabView_tabIcon)
                val text = getString(R.styleable.MainTabView_tabText)
                mColorNormal = getColor(R.styleable.MainTabView_tabColorNormal, mColorNormal)
                mColorSelected = getColor(R.styleable.MainTabView_tabColorSelected, mColorSelected)

                vIcon.setImageDrawable(mDrawable)
                vIcon.setColorFilter(mColorNormal)

                vText.text = text
                vText.setTextColor(mColorNormal)
                recycle()
            }
        }
    }

    fun setTabNormal() {
        isTabSelected = false
        vIcon.setColorFilter(mColorNormal)
        vText.setTextColor(mColorNormal)
    }

    fun setTabSelected() {
        isTabSelected = true
        vIcon.setColorFilter(mColorSelected)
        vText.setTextColor(mColorSelected)
    }
}