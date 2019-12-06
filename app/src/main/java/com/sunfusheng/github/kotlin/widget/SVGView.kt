package com.sunfusheng.github.kotlin.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.core.graphics.PathParser
import androidx.core.view.ViewCompat
import com.sunfusheng.github.kotlin.R

/**
 * @author sunfusheng
 * @since 2019-11-11
 */
class SVGView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    val STATE_NOT_STARTED = 0
    val STATE_TRACE_STARTED = 1
    val STATE_FILL_STARTED = 2
    val STATE_FINISHED = 3

    private val TAG = "SVGView"

    private val INTERPOLATOR = DecelerateInterpolator()

    private fun constrain(min: Float, max: Float, v: Float): Float {
        return Math.max(min, Math.min(max, v))
    }

    private var mTraceTime = 2000
    private var mTraceTimePerGlyph = 1000
    private var mFillStart = 1200
    private var mFillTime = 1000
    private val MARKER_LENGTH_DIP = 16
    private var mTraceResidueColors: IntArray? = null
    private var mTraceColors: IntArray? = null
    private var mViewportWidth: Float = 0.toFloat()
    private var mViewportHeight: Float = 0.toFloat()
    private var mViewport = PointF(mViewportWidth, mViewportHeight)
    private var aspectRatioWidth = 1f
    private var aspectRatioHeight = 1f

    private val mFillPaint by lazy {
        Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
        }
    }
    private var mFillColors: IntArray? = null
    private lateinit var mGlyphData: MutableList<GlyphData>
    private var mGlyphStrings: Array<out String>? = null
    private var mMarkerLength: Float = 0.toFloat()
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var mStartTime: Long = 0

    private var mState = STATE_NOT_STARTED
    private var mOnStateChangeListener: OnStateChangeListener? = null


    init {
        mMarkerLength = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            MARKER_LENGTH_DIP.toFloat(),
            resources.displayMetrics
        )

        mTraceColors = intArrayOf(Color.BLACK)
        mTraceResidueColors = intArrayOf(0x32000000)

        attrs?.let {
            context.obtainStyledAttributes(attrs, R.styleable.SVGView).apply {
                mViewportWidth = getInt(R.styleable.SVGView_svgImageSizeX, 512).toFloat()
                aspectRatioWidth = getInt(R.styleable.SVGView_svgImageSizeX, 512).toFloat()
                mViewportHeight = getInt(R.styleable.SVGView_svgImageSizeY, 512).toFloat()
                aspectRatioHeight = getInt(R.styleable.SVGView_svgImageSizeY, 512).toFloat()
                mTraceTime = getInt(R.styleable.SVGView_svgTraceTime, 2000)
                mTraceTimePerGlyph = getInt(R.styleable.SVGView_svgTraceTimePerGlyph, 1000)
                mFillStart = getInt(R.styleable.SVGView_svgFillStart, 1200)
                mFillTime = getInt(R.styleable.SVGView_svgFillTime, 1000)
                val glyphStringsId = getResourceId(R.styleable.SVGView_svgGlyphStrings, 0)
                val traceResidueColorsId =
                    getResourceId(R.styleable.SVGView_svgTraceResidueColors, 0)
                val traceColorsId = getResourceId(R.styleable.SVGView_svgTraceColors, 0)
                val fillColorsId = getResourceId(R.styleable.SVGView_svgFillColors, 0)

                if (glyphStringsId != 0) {
                    setGlyphStrings(*resources.getStringArray(glyphStringsId))
                    setTraceResidueColor(Color.argb(50, 0, 0, 0))
                    setTraceColor(Color.BLACK)
                }
                if (traceResidueColorsId != 0) {
                    setTraceResidueColors(resources.getIntArray(traceResidueColorsId))
                }
                if (traceColorsId != 0) {
                    setTraceColors(resources.getIntArray(traceColorsId))
                }
                if (fillColorsId != 0) {
                    setFillColors(resources.getIntArray(fillColorsId))
                }
                mViewport = PointF(mViewportWidth, mViewportHeight)
                recycle()
            }
        }

        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }

    fun setViewportSize(viewportWidth: Float, viewportHeight: Float) {
        mViewportWidth = viewportWidth
        mViewportHeight = viewportHeight
        aspectRatioWidth = viewportWidth
        aspectRatioHeight = viewportHeight
        mViewport = PointF(mViewportWidth, mViewportHeight)
        requestLayout()
    }

    fun setGlyphStrings(vararg glyphStrings: String) {
        mGlyphStrings = glyphStrings
    }

    fun setTraceResidueColors(traceResidueColors: IntArray) {
        mTraceResidueColors = traceResidueColors
    }

    fun setTraceColors(traceColors: IntArray) {
        mTraceColors = traceColors
    }

    fun setFillColors(fillColors: IntArray) {
        mFillColors = fillColors
    }

    fun setTraceResidueColor(color: Int) {
        if (mGlyphStrings == null) {
            throw RuntimeException("You need to set the glyphs first.")
        }
        val length = mGlyphStrings!!.size
        val colors = IntArray(length)
        for (i in 0 until length) {
            colors[i] = color
        }
        setTraceResidueColors(colors)
    }

    fun setTraceColor(color: Int) {
        if (mGlyphStrings == null) {
            throw RuntimeException("You need to set the glyphs first.")
        }
        val length = mGlyphStrings!!.size
        val colors = IntArray(length)
        for (i in 0 until length) {
            colors[i] = color
        }
        setTraceColors(colors)
    }

    fun setFillColor(color: Int) {
        if (mGlyphStrings == null) {
            throw RuntimeException("You need to set the glyphs first.")
        }
        val length = mGlyphStrings!!.size
        val colors = IntArray(length)
        for (i in 0 until length) {
            colors[i] = color
        }
        setFillColors(colors)
    }

    fun start() {
        mStartTime = System.currentTimeMillis()
        changeState(STATE_TRACE_STARTED)
        ViewCompat.postInvalidateOnAnimation(this)
    }

    fun reset() {
        mStartTime = 0
        changeState(STATE_NOT_STARTED)
        ViewCompat.postInvalidateOnAnimation(this)
    }

    fun setToFinishedFrame() {
        mStartTime = 1
        changeState(STATE_FINISHED)
        ViewCompat.postInvalidateOnAnimation(this)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
        rebuildGlyphData()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var width = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)

        var height = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        if (height <= 0 && width <= 0 && heightMode == MeasureSpec.UNSPECIFIED && widthMode == MeasureSpec.UNSPECIFIED) {
            width = 0
            height = 0
        } else if (height <= 0 && heightMode == View.MeasureSpec.UNSPECIFIED) {
            height = (width * aspectRatioHeight / aspectRatioWidth).toInt()
        } else if (width <= 0 && widthMode == View.MeasureSpec.UNSPECIFIED) {
            width = (height * aspectRatioWidth / aspectRatioHeight).toInt()
        } else if (width * aspectRatioHeight > aspectRatioWidth * height) {
            width = (height * aspectRatioWidth / aspectRatioHeight).toInt()
        } else {
            height = (width * aspectRatioHeight / aspectRatioWidth).toInt()
        }

        super.onMeasure(
            MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        )
    }

    @SuppressLint("RestrictedApi")
    fun rebuildGlyphData() {

        val X = mWidth / mViewport.x
        val Y = mHeight / mViewport.y

        val scaleMatrix = Matrix()
        val outerRect = RectF(X, X, Y, Y)
        scaleMatrix.setScale(X, Y, outerRect.centerX(), outerRect.centerY())

        mGlyphData = mutableListOf()
        for (i in mGlyphStrings!!.indices) {
            mGlyphData.add(GlyphData())

            try {
                mGlyphData[i].path = PathParser.createPathFromPathData(mGlyphStrings!![i])
                mGlyphData[i].path!!.transform(scaleMatrix)
            } catch (e: Exception) {
                mGlyphData[i].path = Path()
                Log.e(TAG, "Couldn't parse path", e)
            }

            val pm = PathMeasure(mGlyphData[i].path, true)
            while (true) {
                mGlyphData[i].length = Math.max(mGlyphData[i].length, pm.length)
                if (!pm.nextContour()) {
                    break
                }
            }
            mGlyphData[i].paint = Paint()
            mGlyphData[i].paint!!.style = Paint.Style.STROKE
            mGlyphData[i].paint!!.isAntiAlias = true
            mGlyphData[i].paint!!.color = Color.WHITE
            mGlyphData[i].paint!!.strokeWidth =
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1f, resources.displayMetrics)
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (mState == STATE_NOT_STARTED || mGlyphData == null) {
            return
        }

        val t = System.currentTimeMillis() - mStartTime

        // Draw outlines (starts as traced)
        for (i in mGlyphData.indices) {
            val phase = constrain(
                0f, 1f,
                (t - (mTraceTime - mTraceTimePerGlyph).toFloat() * i.toFloat() * 1f / mGlyphData.size) * 1f / mTraceTimePerGlyph
            )
            val distance = INTERPOLATOR.getInterpolation(phase) * mGlyphData[i].length
            mGlyphData[i].paint!!.color = mTraceResidueColors!![i]
            mGlyphData[i].paint!!.pathEffect = DashPathEffect(
                floatArrayOf(distance, mGlyphData[i].length), 0f
            )
            canvas.drawPath(mGlyphData[i].path!!, mGlyphData[i].paint!!)

            mGlyphData[i].paint!!.color = mTraceColors!![i]
            mGlyphData[i].paint!!.pathEffect = DashPathEffect(
                floatArrayOf(
                    0f,
                    distance,
                    if (phase > 0) mMarkerLength else 0f,
                    mGlyphData[i].length
                ), 0f
            )
            canvas.drawPath(mGlyphData[i].path!!, mGlyphData[i].paint!!)
        }

        if (t > mFillStart) {
            if (mState < STATE_FILL_STARTED) {
                changeState(STATE_FILL_STARTED)
            }

            // If after fill start, draw fill
            val phase = constrain(0f, 1f, (t - mFillStart) * 1f / mFillTime)
            for (i in mGlyphData.indices) {
                val glyphData = mGlyphData[i]
                val fillColor = mFillColors!![i]
                val a = (phase * (Color.alpha(fillColor).toFloat() / 255.toFloat()) * 255f).toInt()
                val r = Color.red(fillColor)
                val g = Color.green(fillColor)
                val b = Color.blue(fillColor)
                mFillPaint.setARGB(a, r, g, b)
                canvas.drawPath(glyphData.path!!, mFillPaint)
            }
        }

        if (t < mFillStart + mFillTime) {
            // draw next frame if animation isn't finished
            ViewCompat.postInvalidateOnAnimation(this)
        } else {
            changeState(STATE_FINISHED)
        }
    }

    private fun changeState(state: Int) {
        if (mState == state) {
            return
        }

        mState = state
        if (mOnStateChangeListener != null) {
            mOnStateChangeListener!!.onStateChange(state)
        }
    }

    fun getState(): Int {
        return mState
    }

    fun setOnStateChangeListener(onStateChangeListener: OnStateChangeListener) {
        mOnStateChangeListener = onStateChangeListener
    }

    interface OnStateChangeListener {
        fun onStateChange(state: Int)
    }

    private class GlyphData {
        internal var path: Path? = null
        internal var paint: Paint? = null
        internal var length: Float = 0.toFloat()
    }
}