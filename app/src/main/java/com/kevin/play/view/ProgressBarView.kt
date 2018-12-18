package com.kevin.play.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.kevin.play.R
import com.kevin.play.util.DisplayUtils

/**
 * Created by Kevin on 2018/12/18<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class ProgressBarView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    var totalProgress: Int = 0
    private var progress: Float = 0.toFloat()
    private var mProgress: Float = 0.toFloat()
    private val mPaint: Paint
    private val mBackgroundPaint: Paint
    private var mColor: Int = 0
    private var mBackgroundBarColor: Int = 0
    private val mProgressWith: Float = 0.toFloat()
    private var mDefaultHeight: Int = 0
    private val mDefaultWidth: Int
    private val mDefaultPadding: Int = 0
    var textSize: Int = 0
    private val mR: Int

    private var mLeft: Int = 0
    private var mTop: Int = 0
    private var mRight: Int = 0
    private var mBottom: Int = 0
    private var mPaddingLeft: Int = 0
    private var mPaddingRight: Int = 0
    private var mPaddingTop: Int = 0
    private var mPaddingBottom: Int = 0
    private val defaultPadding: Int
    private val defaultTextMarginLeft: Int//进度百分百距离进度条的距离
    private val defaultTextMarginTop: Int//进度百分百距离进度条的距离
    private var mTextPosition: Int = 0

    var currentProgress: Int
        get() = mProgress.toInt()
        set(mProgress) {
            this.mProgress = mProgress.toFloat()
        }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressBarView)
        if (typedArray != null) {
            mBackgroundBarColor = typedArray.getColor(R.styleable.ProgressBarView_progressBarBackgroundColor, -0x550f0c0d)
            mColor = typedArray.getColor(R.styleable.ProgressBarView_progressBarColor, -0xd07604)
            totalProgress = typedArray.getInt(R.styleable.ProgressBarView_progressBarTotalProgress, 100)
            mProgress = typedArray.getInt(R.styleable.ProgressBarView_progressBarProgress, 0).toFloat()
            mTextPosition = typedArray.getInt(R.styleable.ProgressBarView_position, NONE)
            textSize = if (mTextPosition == TOP) {

                typedArray.getDimensionPixelSize(R.styleable.ProgressBarView_progressBarTextSize, DisplayUtils.sp2px(getContext(), 12))
            } else {

                typedArray.getDimensionPixelSize(R.styleable.ProgressBarView_progressBarTextSize, DisplayUtils.sp2px(getContext(), 14))
            }

        }

        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mBackgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)

        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        val screenWidth = dm.widthPixels         // 屏幕宽度（像素）
        val screenHeight = dm.heightPixels       // 屏幕高度（像素）
        defaultPadding = DisplayUtils.dp2px(getContext(), 5f)
        mDefaultWidth = screenWidth * 3 / 4//默认2/3屏幕
        mDefaultHeight = if (mTextPosition == TOP) {
            DisplayUtils.dip2px(getContext(), 40f)
        } else if (mTextPosition == NONE) {
            DisplayUtils.dip2px(getContext(), 3f)
        } else {
            DisplayUtils.dip2px(getContext(), 20f)

        }
        defaultTextMarginLeft = DisplayUtils.dp2px(getContext(), 5f)
        defaultTextMarginTop = DisplayUtils.dp2px(getContext(), 1f)

        mR = DisplayUtils.dp2px(getContext(), 3f)

        Log.d(TAG, "屏幕宽度（像素）：$screenWidth")
        Log.d(TAG, "屏幕高度（像素）：$screenHeight")


    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = measureWidth(widthMeasureSpec)
        var height = 0
        try {
            height = measureHeight(heightMeasureSpec)
        } catch (e: Exception) {
            e.printStackTrace()
        }

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

    @Throws(Exception::class)
    private fun measureHeight(measureSpec: Int): Int {
        var result = mDefaultHeight
        val specMode = View.MeasureSpec.getMode(measureSpec)
        val specSize = View.MeasureSpec.getSize(measureSpec)
        when (specMode) {
            View.MeasureSpec.UNSPECIFIED -> result = Math.min(result, specSize)
            View.MeasureSpec.AT_MOST -> result = Math.min(result, specSize)
            View.MeasureSpec.EXACTLY -> {
                if (mTextPosition == TOP && specSize < mDefaultHeight) {
                    throw Exception("The progressbar's height is too small to show")
                }
                result = specSize
            }
        }
        return result
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        mPaddingLeft = getPaddingLeft()
        mPaddingRight = getPaddingRight()
        mPaddingTop = getPaddingTop()
        mPaddingBottom = getPaddingBottom()

//        if (mPaddingLeft <= defaultPadding) {
//            mPaddingLeft = defaultPadding
//        }
//        if (mPaddingRight <= defaultPadding) {
//            mPaddingRight = defaultPadding
//        }
//        if (mPaddingBottom <= defaultPadding) {
//            mPaddingBottom = defaultPadding
//        }
//        if (mPaddingTop <= defaultPadding) {
//            mPaddingTop = defaultPadding
//        }


        mLeft = left + mPaddingLeft
        mTop = top + mPaddingTop
        mRight = right - mPaddingRight
        mBottom = bottom - mPaddingBottom

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPaint.style = Paint.Style.FILL
        mPaint.color = mColor
        mBackgroundPaint.style = Paint.Style.FILL
        mBackgroundPaint.color = mBackgroundBarColor


        val bound = Rect()
        val x = progress.toInt().toString() + "%"
        mPaint.textSize = textSize.toFloat()
        val total = totalProgress.toString() + "%"
        mPaint.getTextBounds(total, 0, total.length, bound)
        val boundLeft = bound.left
        val boundTop = bound.top
        val boundRight = bound.right
        val boundBottom = bound.bottom
        val progressBarTotalWidth = mRight - mLeft


        when (mTextPosition) {
            RIGHT -> drawRightProgress(canvas, bound, x, boundTop, boundBottom, progressBarTotalWidth)
            INTER -> drawInterProgress(canvas, bound, x, boundTop, boundBottom, progressBarTotalWidth)
            TOP -> drawTopProgress(canvas, progressBarTotalWidth)
            NONE -> drawProgress(canvas, progressBarTotalWidth)
        }


    }

    private fun drawProgress(canvas: Canvas, progressBarTotalWidth: Int) {
        canvas.save()
        val centerY = (mTop + mBottom) / 2
        val percent = progress / totalProgress
        val progressBarRight = mLeft + percent * progressBarTotalWidth
        val rectF = RectF(mLeft.toFloat(), mTop.toFloat(), progressBarRight, mBottom.toFloat())
        val rectB = RectF(mLeft.toFloat(), mTop.toFloat(), (mLeft + progressBarTotalWidth).toFloat(), mBottom.toFloat())
        canvas.translate((-mLeft + mPaddingLeft).toFloat(), (-mTop + mPaddingTop).toFloat())
        canvas.drawRoundRect(rectB, mR.toFloat(), mR.toFloat(), mBackgroundPaint)
        canvas.drawRoundRect(rectF, mR.toFloat(), mR.toFloat(), mPaint)
        canvas.restore()
    }

    /**
     * 进度百分比在进度条上方
     *
     * @param canvas
     * @param progressBarTotalWidth
     */
    private fun drawTopProgress(canvas: Canvas, progressBarTotalWidth: Int) {
        val textBound = Rect()
        val text = Rect()
        val xx = progress.toInt().toString()
        val totalX = totalProgress.toString()
        mPaint.textSize = textSize.toFloat()
        mPaint.getTextBounds(totalX, 0, totalX.length, textBound)
        mPaint.getTextBounds(xx, 0, xx.length, text)
        val lineWidth = textBound.width() + DisplayUtils.dip2px(context, 2f)
        val percent = progress / totalProgress
        val progressBarTop = (mTop.toDouble() + textBound.height() * 2.5 + defaultTextMarginTop.toDouble()).toFloat()
        val progressBarRight = mLeft + percent * progressBarTotalWidth
        val rectF = RectF(mLeft.toFloat(), progressBarTop, progressBarRight, mBottom.toFloat())
        val rectB = RectF(mLeft.toFloat(), progressBarTop, (mLeft + progressBarTotalWidth).toFloat(), mBottom.toFloat())

        var cursor = mLeft.toFloat()//游标左侧
        if (progressBarRight < cursor + lineWidth / 2) {
            cursor = mLeft.toFloat()
        } else {
            cursor = progressBarRight - lineWidth / 2
            if (cursor + lineWidth >= mRight) {
                cursor = (mRight - lineWidth).toFloat()
            }
        }
        val cursorRectHeight = (textBound.height() * 1.5).toFloat()
        val path = Path()
        path.moveTo(cursor + lineWidth / 2, mTop.toFloat())
        path.rLineTo((lineWidth / 2).toFloat(), 0f)
        path.rLineTo(0f, cursorRectHeight)
        path.rLineTo((-lineWidth / 2).toFloat(), textBound.height().toFloat())
        path.rLineTo((-lineWidth / 2).toFloat(), (-textBound.height()).toFloat())
        path.rLineTo(0f, -cursorRectHeight)
        path.close()

        val textLeft = cursor + lineWidth / 2 - (text.left + text.right) / 2

        canvas.save()

        canvas.translate((-mLeft + mPaddingLeft).toFloat(), (-mTop + mPaddingTop).toFloat())
        canvas.drawRoundRect(rectB, mR.toFloat(), mR.toFloat(), mBackgroundPaint)
        canvas.drawRoundRect(rectF, mR.toFloat(), mR.toFloat(), mPaint)
        val centerY = mTop + textBound.height() * 3 / 4 - (textBound.top + textBound.bottom) / 2
        canvas.drawPath(path, mPaint)
        mPaint.color = Color.WHITE
        canvas.drawText(xx, textLeft, centerY.toFloat(), mPaint)
        canvas.restore()
    }

    /**
     * 百分比显示在进度条之间
     *
     * @param canvas
     * @param bound
     * @param x
     * @param boundTop
     * @param boundBottom
     * @param progressBarTotalWidth
     */
    private fun drawInterProgress(canvas: Canvas, bound: Rect, x: String, boundTop: Int, boundBottom: Int, progressBarTotalWidth: Int) {
        canvas.save()
        val centerY = (mTop + mBottom) / 2
        val percent = progress / totalProgress
        val progressBarRight = mLeft + percent * progressBarTotalWidth
        val rectF = RectF(mLeft.toFloat(), mTop.toFloat(), progressBarRight, mBottom.toFloat())
        val rectB = RectF(mLeft.toFloat(), mTop.toFloat(), (mLeft + progressBarTotalWidth).toFloat(), mBottom.toFloat())
        canvas.translate((-mLeft + mPaddingLeft).toFloat(), (-mTop + mPaddingTop).toFloat())
        canvas.drawRoundRect(rectB, mR.toFloat(), mR.toFloat(), mBackgroundPaint)
        canvas.drawRoundRect(rectF, mR.toFloat(), mR.toFloat(), mPaint)
        mPaint.color = Color.WHITE
        var textLeft = mLeft.toFloat()
        if (mLeft + bound.width() / 2 > progressBarRight * 3 / 4) {
            textLeft = textLeft
        } else {
            textLeft = progressBarRight * 3 / 4 - (bound.left + bound.right) / 2
            if (textLeft + bound.width() / 2 >= (mLeft + progressBarTotalWidth) * 3 / 4) {
                textLeft = ((mLeft + progressBarTotalWidth) * 3 / 4).toFloat()
            }
        }
        canvas.drawText(x, textLeft, (centerY - (boundTop + boundBottom) / 2).toFloat(), mPaint)
        canvas.restore()
    }

    /**
     * text progress show right of the progress bar
     *
     * @param canvas
     * @param bound
     * @param x
     * @param boundTop
     * @param boundBottom
     * @param progressBarTotalWidth
     */
    private fun drawRightProgress(canvas: Canvas, bound: Rect, x: String, boundTop: Int, boundBottom: Int, progressBarTotalWidth: Int) {
        canvas.save()
        val progressBarWidth = progressBarTotalWidth - bound.width() - defaultTextMarginLeft
        val centerY = (mTop + mBottom) / 2
        val rectF = RectF(mLeft.toFloat(), mTop.toFloat(), mLeft + progress / totalProgress * progressBarWidth, mBottom.toFloat())
        val rectB = RectF(mLeft.toFloat(), mTop.toFloat(), (mLeft + progressBarWidth).toFloat(), mBottom.toFloat())
        canvas.translate((-mLeft + mPaddingLeft).toFloat(), (-mTop + mPaddingTop).toFloat())
        canvas.drawRoundRect(rectB, mR.toFloat(), mR.toFloat(), mBackgroundPaint)
        canvas.drawRoundRect(rectF, mR.toFloat(), mR.toFloat(), mPaint)
        canvas.drawText(x, (mLeft + progressBarWidth + defaultTextMarginLeft).toFloat(), (centerY - (boundTop + boundBottom) / 2).toFloat(), mPaint)
        //        canvas.translate(mLeft - mPaddingLeft, mTop - mPaddingTop);
        canvas.restore()
    }


    fun getmColor(): Int {
        return mColor
    }

    fun setmColor(mColor: Int) {
        this.mColor = mColor
    }

    fun getmBackgroundBarColor(): Int {
        return mBackgroundBarColor
    }

    fun setmBackgroundBarColor(mBackgroundBarColor: Int) {
        this.mBackgroundBarColor = mBackgroundBarColor
    }

    @Synchronized
    fun setProgress(progress: Int) {
        this.progress = progress.toFloat()
        postInvalidate()
    }

    @Synchronized
    fun getProgress(): Float {
        return progress
    }

    companion object {
        private val TAG = "ProgressBarView"
        private val RIGHT = 0
        private val INTER = 1
        private val TOP = 2
        private val NONE = 3
    }
}