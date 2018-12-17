package com.kevin.play.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.kevin.play.R

/**
 * Created by Kevin on 2018/12/5<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class TabFragmentAdapter(
    context: Context,
    tabList: IntArray,
    tabIcon: IntArray,
    tabIconFilled: IntArray,
    fragmentList: ArrayList<Fragment>?,
    fm: FragmentManager?
) : FragmentPagerAdapter(fm) {
    var context = context
    var tabList = tabList
    var tabIconList = tabIcon
    var tabIconFilledList = tabIconFilled
    var fragmentList = fragmentList

    override fun getItem(position: Int): Fragment {
        return fragmentList!![position]
    }

    override fun getCount(): Int {
        return tabList.size//To change body of created functions use File | Settings | File Templates.
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.getString(tabList[position])
    }

    open fun getTabView(position: Int): View {
        var view = LayoutInflater.from(context).inflate(R.layout.adapter_tab_fragment, null)
        var tabIcon = view.findViewById<ImageView>(R.id.tabIcon)
        var tabIconFilled = view.findViewById<ImageView>(R.id.tabIconFilled)
        var tabTitle = view.findViewById<TextView>(R.id.tabTitle)
        tabIcon.setImageResource(tabIconList[position])
        tabIconFilled.setImageResource(tabIconFilledList[position])
        tabTitle.text = context.getString(tabList[position])
        if (position == 0) {
            tabTitle.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
//            tabIcon.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary))
            tabIconFilled.imageAlpha = 255
            tabIcon.imageAlpha=0
        } else {
            tabIconFilled.imageAlpha = 0
            tabIcon.imageAlpha=255
        }
        return view
    }
}