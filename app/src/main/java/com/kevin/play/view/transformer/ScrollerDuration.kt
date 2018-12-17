package com.kevin.play.view.transformer

import android.content.Context
import android.view.animation.Interpolator
import android.widget.Scroller

/**
 * Created by Kevin on 2018/12/14<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class ScrollerDuration(var context: Context, var interpolator: Interpolator) : Scroller(context, interpolator) {
    private var mDuration = 600
    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int) {
        super.startScroll(startX, startY, dx, dy, mDuration)
    }

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
        super.startScroll(startX, startY, dx, dy, mDuration)
    }

    open fun setDuration(duration: Int) {
        if (duration >= 300) {
            mDuration = duration
        }
    }
}