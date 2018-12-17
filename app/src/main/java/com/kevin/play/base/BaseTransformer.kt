package com.kevin.play.base

import android.support.v4.view.ViewPager
import android.util.Log
import android.view.View

/**
 * Created by Kevin on 2018/12/14<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
abstract class BaseTransformer : ViewPager.PageTransformer {
    companion object {
        const val TAG = "BaseTransformer"
    }
    override fun transformPage(view: View, position: Float) {
        if (position < 1 && position > -1) {
            //0---->-1 或者-1---0
            if (position > -1 && position < 0) {
                preTransform(view, position)
            }
            //1--->0 或者0--->1
            if (position >= 0 && position < 1) {
                postTransform(view, position)
            }
        }
    }

    open abstract fun preTransform(view: View, position: Float)
    open abstract fun postTransform(view: View, position: Float)
}