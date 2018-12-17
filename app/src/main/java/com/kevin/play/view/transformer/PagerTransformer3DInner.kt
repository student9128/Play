package com.kevin.play.view.transformer

import android.view.View
import com.kevin.play.base.BaseTransformer

/**
 * Created by Kevin on 2018/12/14<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class PagerTransformer3DInner:BaseTransformer(){
    private var mRotation = 1f
    private var mScale = 0.9f

    override fun preTransform(view: View, position: Float) {
        if (Math.abs(position)<=mScale){
            var f:Float = if (1-Math.abs(position)<=mScale)mScale else 1-Math.abs(position)
            view.scaleY= f
        }else{
            view.scaleY = Math.abs(position)
        }
        view.pivotX= view.measuredWidth.toFloat()
        view.pivotY= (view.measuredHeight/2).toFloat()
        view.rotationY = mRotation*Math.abs(position)
    }

    override fun postTransform(view: View, position: Float) {
        view.pivotX= view.measuredWidth.toFloat()
        view.pivotY= (view.measuredHeight/2).toFloat()
        view.rotationY = -mRotation*position
        if (position>1-mScale&&position<1){
            var f:Float = if (position<mScale)mScale else position
            view.scaleY = f
        }else{
            view.scaleY = 1-position
        }
    }

}