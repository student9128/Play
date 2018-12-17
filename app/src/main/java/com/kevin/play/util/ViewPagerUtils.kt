package com.kevin.play.util

import android.content.Context
import android.support.v4.view.ViewPager
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import com.kevin.play.view.transformer.ScrollerDuration

/**
 * Created by Kevin on 2018/12/14<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
object ViewPagerUtils {

    fun setDuration(context: Context, `object`: Any) {
        val scroller = ViewPager::class.java.getDeclaredField("mScroller")
        scroller.isAccessible = true
        var s = ScrollerDuration(context, AccelerateDecelerateInterpolator())
        scroller.set(`object`, s)
    }

    fun setDuration(context: Context, `object`: Any,interpolator: Interpolator) {
        val scroller = ViewPager::class.java.getDeclaredField("mScroller")
        scroller.isAccessible = true
        var s = ScrollerDuration(context, AccelerateDecelerateInterpolator())
        scroller.set(`object`, s)
    }

    fun setDuration(context: Context, `object`: Any, interpolator: Interpolator,duration:Int) {
        val scroller = ViewPager::class.java.getDeclaredField("mScroller")
        scroller.isAccessible = true
        var sd = ScrollerDuration(context,interpolator)
        sd.duration = duration
        var s = sd
        scroller.set(`object`, s)
    }

}