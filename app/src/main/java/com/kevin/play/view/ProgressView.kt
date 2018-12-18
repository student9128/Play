package com.kevin.play.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import com.kevin.play.R
import com.kevin.play.util.DisplayUtils


/**
 * Created by Kevin on 2018/11/19<br></br>
 * Blog:http://student9128.top/<br></br>
 * Describe:<br></br>
 */
class ProgressView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    private var mProgress: Int = 0
    private var progress: Int = 0
    private val mPaint: Paint
    private val mBackgroundPaint: Paint
    var color: Int = 0
    var backgroundCircleColor: Int = 0
    var progressWith: Float = 0.toFloat()
    private val mDefaultHeight: Int
    private val mDefaultWidth: Int
    private val mDefaultPadding: Int = 0
    private var animator: ValueAnimator? = null
    private var textSize: Int = 0
    private var mR: Int = 0
    /**
     * 顺时针还是逆时针，默认false,逆时针
     */
    var isClockWise: Boolean = false//顺时针还是逆时针，默认false,逆时针

    //    private enum ClockWise {
    //        CLOCK_WISE, ANTI_CLOCK_WISE
    //    }

    private var mLeft: Int = 0
    private var mTop: Int = 0
    private var mRight: Int = 0
    private var mBottom: Int = 0
    private var mPaddingLeft: Int = 0
    private var mPaddingRight: Int = 0
    private var mPaddingTop: Int = 0
    private var mPaddingBottom: Int = 0
    private val defaultPadding: Int

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressView)
        if (typedArray != null) {
            backgroundCircleColor = typedArray.getColor(R.styleable.ProgressView_progressBackgroundColor, -0x550f0c0d)
            color = typedArray.getColor(R.styleable.ProgressView_progressColor, -0xd07604)
            progress = typedArray.getInt(R.styleable.ProgressView_progress, 0)
            textSize = typedArray.getDimensionPixelSize(R.styleable.ProgressView_progressTextSize, DisplayUtils.sp2px(getContext(), 14))
            isClockWise = typedArray.getBoolean(R.styleable.ProgressView_clockWise, false)
            progressWith = typedArray.getDimension(R.styleable.ProgressView_progressWidth, DisplayUtils.dip2px(getContext(), 2F).toFloat())
            typedArray.recycle()
        }
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mBackgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)


        defaultPadding = DisplayUtils.dp2px(getContext(), 3F)
        mDefaultWidth = DisplayUtils.dip2px(getContext(), 60F)
        mDefaultHeight = DisplayUtils.dip2px(getContext(), 60F)

//        animator = ValueAnimator.ofFloat(0F, mProgress.toFloat())
//        startAnimation()

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = measureWidth(widthMeasureSpec)
        val height = measureHeight(heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    private fun measureWidth(measureSpec: Int): Int {
        var result = mDefaultWidth
        val specMode = View.MeasureSpec.getMode(measureSpec)
        val specSize = View.MeasureSpec.getSize(measureSpec)
        when (specMode) {
            View.MeasureSpec.UNSPECIFIED -> result = specSize
            View.MeasureSpec.AT_MOST -> result = Math.min(result, specSize)
            View.MeasureSpec.EXACTLY -> result = specSize
        }
        return result
    }

    private fun measureHeight(measureSpec: Int): Int {
        var result = mDefaultHeight
        val specMode = View.MeasureSpec.getMode(measureSpec)
        val specSize = View.MeasureSpec.getSize(measureSpec)
        when (specMode) {
            View.MeasureSpec.UNSPECIFIED -> result = Math.min(result, specSize)
            View.MeasureSpec.AT_MOST -> result = Math.min(result, specSize)
            View.MeasureSpec.EXACTLY -> result = specSize
        }
        return result
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        val width = width
        val height = height
        val measuredWidth = measuredWidth
        val measuredHeight = measuredHeight
        mPaddingLeft = getPaddingLeft()
        mPaddingRight = getPaddingRight()
        mPaddingTop = getPaddingTop()
        mPaddingBottom = getPaddingBottom()

        if (mPaddingLeft <= defaultPadding) {
            mPaddingLeft = defaultPadding
        }
        if (mPaddingRight <= defaultPadding) {
            mPaddingRight = defaultPadding
        }
        if (mPaddingBottom <= defaultPadding) {
            mPaddingBottom = defaultPadding
        }
        if (mPaddingTop <= defaultPadding) {
            mPaddingTop = defaultPadding
        }



        mLeft = left + mPaddingLeft
        mTop = top + mPaddingTop
        mRight = right - mPaddingRight
        mBottom = bottom - mPaddingBottom
        mR = Math.min(getWidth() - mPaddingLeft - mPaddingRight, getHeight() - mPaddingTop - mPaddingBottom) / 2
        Log.w(TAG, "R=$mR")
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


        mPaint.style = Paint.Style.STROKE
        mPaint.color = color
        mPaint.strokeWidth = progressWith
        mBackgroundPaint.style = Paint.Style.STROKE
        mBackgroundPaint.color = backgroundCircleColor
        mBackgroundPaint.strokeWidth = progressWith

        val centerX = (mLeft + mRight) / 2
        val centerY = (mTop + mBottom) / 2
        Log.i(TAG, "centerX=$centerX")
        Log.i(TAG, "centerY=$centerY")
        val rectF = RectF(mLeft.toFloat(), mTop.toFloat(), mRight.toFloat(), mBottom.toFloat())
        val bound = Rect()
        val x = progress.toString() + "%"
        mPaint.textSize = textSize.toFloat()
        mPaint.getTextBounds(x, 0, x.length, bound)//获取bound后在平移画布，否则不居中
        //translate canvas to (0,0),otherwise (0,0) just relative to the canvas
        // the progressView can not be shown
        canvas.save()
        canvas.translate((-mLeft + mPaddingLeft).toFloat(), (-mTop + mPaddingTop).toFloat())
        canvas.drawCircle(centerX.toFloat(), centerY.toFloat(), mR.toFloat(), mBackgroundPaint)
        if (isClockWise) {
            canvas.drawArc(rectF, -90f, -360 * progress / 100F, false, mPaint)
        } else {
            canvas.drawArc(rectF, 0f, 360 * progress / 100F, false, mPaint)
        }
        mPaint.style = Paint.Style.FILL
        canvas.drawText(x, (centerX - (bound.left + bound.right) / 2).toFloat(), (centerY - (bound.top + bound.bottom) / 2).toFloat(), mPaint)
        //        canvas.translate(mLeft - mPaddingLeft, mTop - mPaddingTop);
        canvas.restore()
    }
//
//    private fun startAnimation() {
//        animator?.let {
//            it.setDuration(5000).interpolator = AccelerateDecelerateInterpolator()
//            it.addUpdateListener { animation ->
//                progress = animation.animatedValue as Float//
//                invalidate()
//            }
//            it.start()
//            if (progress >= mProgress) {
//                progress = mProgress.toFloat()
//            }
//        }
//    }
//
//    private fun cancelAnimation() {
//        animator?.let {
//
//            if (it.isRunning) {
//                it.cancel()
//            }
//        }
//    }

    @Synchronized
    fun getmProgress(): Int {
        return progress
    }

    @Synchronized
    fun setmProgress(mProgress: Int) {
        this.progress = mProgress
        postInvalidate()
    }

    fun getTextSize(): Int {
        return textSize
    }

    fun setTextSize(textSize: Int) {
        this.textSize = DisplayUtils.sp2px(context, textSize)
    }

    companion object {
        private val TAG = "ProgressView"
    }
}