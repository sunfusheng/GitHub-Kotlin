package com.sunfusheng.github.kotlin.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.NonNull
import com.sunfusheng.github.kotlin.R

/**
 * @author sunfusheng
 * @since 2019-11-18
 */
object ViewUtil {

    fun setBackgroundKeepingPadding(view: View, drawable: Drawable) {
        val padding =
            intArrayOf(view.paddingLeft, view.paddingTop, view.paddingRight, view.paddingBottom)
        view.background = drawable
        view.setPadding(padding[0], padding[1], padding[2], padding[3])
    }

    fun setBackgroundKeepingPadding(view: View, backgroundResId: Int) {
        setBackgroundKeepingPadding(view, view.resources.getDrawable(backgroundResId))
    }

    fun setBackgroundColorKeepPadding(view: View, @ColorInt color: Int) {
        val padding =
            intArrayOf(view.paddingLeft, view.paddingTop, view.paddingRight, view.paddingBottom)
        view.setBackgroundColor(color)
        view.setPadding(padding[0], padding[1], padding[2], padding[3])
    }

    @ColorInt
    fun getPrimaryColor(@NonNull context: Context): Int {
        return getColorAttr(context, R.attr.colorPrimary)
    }

    @ColorInt
    fun getPrimaryDarkColor(@NonNull context: Context): Int {
        return getColorAttr(context, R.attr.colorPrimaryDark)
    }

    @ColorInt
    fun getAccentColor(@NonNull context: Context): Int {
        return getColorAttr(context, R.attr.colorAccent)
    }

    @ColorInt
    fun getPrimaryTextColor(@NonNull context: Context): Int {
        return getColorAttr(context, android.R.attr.textColorPrimary)
    }

    @ColorInt
    fun getSecondaryTextColor(@NonNull context: Context): Int {
        return getColorAttr(context, android.R.attr.textColorSecondary)
    }

    @ColorInt
    fun getWindowBackground(@NonNull context: Context): Int {
        return getColorAttr(context, android.R.attr.windowBackground)
    }

    @ColorInt
    fun getCommonBackground(@NonNull context: Context): Int {
        return context.resources.getColor(R.color.background_common)
    }

    @ColorInt
    private fun getColorAttr(@NonNull context: Context, attr: Int): Int {
        val theme = context.theme
        val typedArray = theme.obtainStyledAttributes(intArrayOf(attr))
        val color = typedArray.getColor(0, Color.LTGRAY)
        typedArray.recycle()
        return color
    }


    private fun getBitmapFromResource(vectorDrawable: VectorDrawable): Bitmap {
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        vectorDrawable.draw(canvas)
        return bitmap
    }

    fun getRGBColor(colorValue: Int, withAlpha: Boolean): String {
        val r = colorValue shr 16 and 0xff
        val g = colorValue shr 8 and 0xff
        val b = colorValue and 0xff
        val a = colorValue shr 24 and 0xff
        var red = Integer.toHexString(r)
        var green = Integer.toHexString(g)
        var blue = Integer.toHexString(b)
        var alpha = Integer.toHexString(a)
        red = fixColor(red)
        green = fixColor(green)
        blue = fixColor(blue)
        alpha = fixColor(alpha)
        return if (withAlpha) alpha + red + green + blue else red + green + blue
    }

    private fun fixColor(@NonNull colorStr: String): String {
        return if (colorStr.length == 1) "0$colorStr" else colorStr
    }

    /**
     * darkness < 0.5 is a light color, else is a dark color
     *
     * @param color
     * @return
     */
    fun isLightColor(color: Int): Boolean {
        val darkness =
            1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
        return darkness < 0.5
    }
}