package com.kevin.play.view.transformer

import android.view.View
import com.kevin.play.base.BaseTransformer

/**
 * Created by Kevin on 2018/12/14<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class PagerTransformerAlpha : BaseTransformer() {
    private var mAlpha=1.0f
    override fun preTransform(view: View, position: Float) {
        if (Math.abs(position) < mAlpha) {
            view.alpha=1-Math.abs(position)
        }else{
            view.alpha = mAlpha
        }
    }

    override fun postTransform(view: View, position: Float) {
        view.alpha = 1-position
    }

}