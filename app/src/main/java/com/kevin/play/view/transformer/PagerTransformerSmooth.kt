package com.kevin.play.view.transformer

import android.view.View
import com.kevin.play.base.BaseTransformer

/**
 * Created by Kevin on 2018/12/14<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class PagerTransformerSmooth : BaseTransformer() {
    private var mScale = 0.95f
    override fun preTransform(view: View, position: Float) {
        var scale = if (1 - Math.abs(position) < mScale) mScale else 1 - Math.abs(position)
        view.scaleX = scale
        view.scaleY = scale
    }

    override fun postTransform(view: View, position: Float) {
        var scale = if ((1 - position) < mScale) mScale else (1 - position)
        view.scaleX = scale
        view.scaleY = scale
    }
}