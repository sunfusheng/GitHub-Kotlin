package com.sunfusheng.github.kotlin.widget.radiusview

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import com.sunfusheng.github.kotlin.util.ViewUtil

/**
 * @author sunfusheng
 * @since 2019-11-18
 */
class RadiusButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatButton(context, attrs, defStyleAttr) {

    init {
        val drawable = RadiusGradientDrawable.fromAttributeSet(context, attrs, defStyleAttr)
        ViewUtil.setBackgroundKeepingPadding(this, drawable);
    }
}