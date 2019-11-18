package com.sunfusheng.github.kotlin.widget.radiusview

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.AttributeSet
import androidx.annotation.Nullable
import com.sunfusheng.github.kotlin.R

/**
 * @author sunfusheng
 * @since 2019-11-18
 */
class RadiusGradientDrawable : GradientDrawable() {

    /**
     * 圆角大小是否自适应为 View 高度的一半
     */
    private var mRadiusAdjustBounds = true
    private var mFillColors: ColorStateList? = null
    private var mStrokeWidth = 0
    private var mStrokeColors: ColorStateList? = null

    /**
     * 设置按钮的背景色（只支持纯色,不支持 Bitmap 或 Drawable）
     */
    fun setBgData(@Nullable colors: ColorStateList?) {
        if (hasNativeStateListAPI()) {
            super.setColor(colors)
        } else {
            mFillColors = colors
            val currentColor: Int
            if (colors == null) {
                currentColor = Color.TRANSPARENT
            } else {
                currentColor = colors.getColorForState(state, 0)
            }
            setColor(currentColor)
        }
    }

    /**
     * 设置按钮的描边粗细和颜色
     */
    fun setStrokeData(width: Int, @Nullable colors: ColorStateList?) {
        if (hasNativeStateListAPI()) {
            super.setStroke(width, colors)
        } else {
            mStrokeWidth = width
            mStrokeColors = colors
            val currentColor: Int
            if (colors == null) {
                currentColor = Color.TRANSPARENT
            } else {
                currentColor = colors.getColorForState(state, 0)
            }
            setStroke(width, currentColor)
        }
    }

    private fun hasNativeStateListAPI(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
    }

    /**
     * 设置圆角大小是否自动适应为 View 高度的一半
     */
    fun setIsRadiusAdjustBounds(isRadiusAdjustBounds: Boolean) {
        mRadiusAdjustBounds = isRadiusAdjustBounds
    }

    override fun onStateChange(stateSet: IntArray): Boolean {
        var superRet = super.onStateChange(stateSet)
        if (mFillColors != null) {
            val color = mFillColors!!.getColorForState(stateSet, 0)
            setColor(color)
            superRet = true
        }
        if (mStrokeColors != null) {
            val color = mStrokeColors!!.getColorForState(stateSet, 0)
            setStroke(mStrokeWidth, color)
            superRet = true
        }
        return superRet
    }

    override fun isStateful(): Boolean {
        return (mFillColors != null && mFillColors!!.isStateful
                || mStrokeColors != null && mStrokeColors!!.isStateful
                || super.isStateful())
    }

    override fun onBoundsChange(r: Rect) {
        super.onBoundsChange(r)
        if (mRadiusAdjustBounds) {
            // 修改圆角为短边的一半
            cornerRadius = (Math.min(r.width(), r.height()) / 2).toFloat()
        }
    }

    companion object {
        fun fromAttributeSet(
            context: Context,
            attrs: AttributeSet?,
            defStyleAttr: Int
        ): RadiusGradientDrawable {

            context.obtainStyledAttributes(attrs, R.styleable.RadiusView, defStyleAttr, 0).apply {
                val colorBg = getColorStateList(R.styleable.RadiusView_backgroundColor)
                val colorBorder = getColorStateList(R.styleable.RadiusView_borderColor)
                val borderWidth = getDimensionPixelSize(R.styleable.RadiusView_borderWidth, 0)
                var isRadiusAdjustBounds =
                    getBoolean(R.styleable.RadiusView_isRadiusAdjustBounds, false)
                val radius = getDimensionPixelSize(R.styleable.RadiusView_radius, 0)
                val radiusTopLeft = getDimensionPixelSize(R.styleable.RadiusView_radiusTopLeft, 0)
                val radiusTopRight =
                    getDimensionPixelSize(R.styleable.RadiusView_radiusTopRight, 0)
                val radiusBottomLeft =
                    getDimensionPixelSize(R.styleable.RadiusView_radiusBottomLeft, 0)
                val radiusBottomRight =
                    getDimensionPixelSize(R.styleable.RadiusView_radiusBottomRight, 0)
                recycle()

                val bg = RadiusGradientDrawable()
                bg.setBgData(colorBg)
                bg.setStrokeData(borderWidth, colorBorder)
                if (radiusTopLeft > 0 || radiusTopRight > 0 || radiusBottomLeft > 0 || radiusBottomRight > 0) {
                    val radii = floatArrayOf(
                        radiusTopLeft.toFloat(),
                        radiusTopLeft.toFloat(),
                        radiusTopRight.toFloat(),
                        radiusTopRight.toFloat(),
                        radiusBottomRight.toFloat(),
                        radiusBottomRight.toFloat(),
                        radiusBottomLeft.toFloat(),
                        radiusBottomLeft.toFloat()
                    )
                    bg.cornerRadii = radii
                    isRadiusAdjustBounds = false
                } else {
                    bg.cornerRadius = radius.toFloat()
                    if (radius > 0) {
                        isRadiusAdjustBounds = false
                    }
                }
                bg.setIsRadiusAdjustBounds(isRadiusAdjustBounds)
                return bg
            }
        }
    }
}