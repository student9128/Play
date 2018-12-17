package com.kevin.play.view.transformer

import android.view.View
import com.kevin.play.base.BaseTransformer

/**
 * Created by Kevin on 2018/12/14<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class PagerTransformerCover : BaseTransformer() {
    private var mScale = 0.8f
    override fun preTransform(view: View, position: Float) {
    }

    /**
     * 只让右侧的图片显示效果
     */
    override fun postTransform(view: View, position: Float) {
        var scaleFactor = mScale + (1 - mScale) * (1-position)
        view.scaleX = scaleFactor
        view.scaleY = scaleFactor
        view.alpha = 1-position
        view.translationX = view.measuredWidth * (-position)
    }
}