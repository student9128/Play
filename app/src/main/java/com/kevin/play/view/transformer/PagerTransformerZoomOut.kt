package com.kevin.play.view.transformer

import android.util.Log
import android.view.View
import com.kevin.play.base.BaseTransformer

/**
 * Created by Kevin on 2018/12/14<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class PagerTransformerZoomOut : BaseTransformer() {
    companion object {
        const val TAG = "PagerTransformerZoomOut"
    }

    override fun preTransform(view: View, position: Float) {
        var scale = 1 + Math.abs(position)
        view.scaleX = scale
        view.scaleY = scale
        view.pivotX = (view.measuredWidth / 2).toFloat()
        view.pivotY = (view.measuredHeight / 2).toFloat()
        view.alpha = 1 + position
        view.translationX = -view.measuredWidth * position

    }

    override fun postTransform(view: View, position: Float) {
        //左滑的时候1-->0,右滑0-->1
        var scale = 1 - position
        view.scaleX = scale
        view.scaleY = scale
        view.pivotX = (view.measuredWidth / 2).toFloat()
        view.pivotY = (view.measuredHeight / 2).toFloat()
        view.alpha = 1 - position
        view.translationX = -view.measuredWidth * position
    }
}