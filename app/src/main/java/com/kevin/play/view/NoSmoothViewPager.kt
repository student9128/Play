package com.kevin.play.view

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet

/**
 * Created by Kevin on 2018/12/5<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class NoSmoothViewPager(context: Context,attrs: AttributeSet) : ViewPager(context,attrs) {

    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item,false)
    }
}