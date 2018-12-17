package com.kevin.play.view.transformer

import android.view.View
import com.kevin.play.base.BaseTransformer

/**
 * Created by Kevin on 2018/12/14<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class PagerTransformer3D : BaseTransformer() {
    private var rotation = 45F
    override fun preTransform(view: View, position: Float) {
        view.pivotX = view.measuredWidth.toFloat()
        view.pivotY = (view.measuredHeight / 2).toFloat()
        view.rotationY = rotation * position//position为负
    }

    override fun postTransform(view: View, position: Float) {
        view.pivotX = 0F
        view.pivotY = (view.measuredHeight / 2).toFloat()
        view.rotationY = rotation * position//position为正
    }

    fun setRotation(rotation: Float) {
        this.rotation = rotation
    }
}