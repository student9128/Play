package com.kevin.play.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.kevin.play.R
import com.kevin.play.bean.HomeBannerData

/**
 * Created by Kevin on 2018/12/14<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class BannerAdapter(context: Context, data: MutableList<HomeBannerData>) : PagerAdapter() {
    var context = context
    var data = data

    open fun addData(d: List<HomeBannerData>) {
        data.clear()
        val get = d[0]
        if (d.size > 1) {
            val get1 = d[d.size - 1]
            data.add(0, get1)
        }
        data.addAll(d)
        data.add(data.size, get)

        notifyDataSetChanged()
    }

    override fun isViewFromObject(view: View, a: Any): Boolean {
        return view == a
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var p = position
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_banner, null)
        val ivBanner = view.findViewById<ImageView>(R.id.ivBanner)
//        when (position) {
//            0 -> p = count - 1
//            count - 2 -> p = 0
//            else -> p -= 1
//        }
        val bannerData = data[position]
//        with(bannerData) {
//            GlideApp.with(context)
//                .load(imagePath)
//                .apply(RequestOptions().centerCrop())
//                .into(ivBanner)
//        }
//        ivBanner.setOnClickListener {
//            listener!!.onViewPagerClick(position)
//        }
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)

    }

    interface OnViewPagerClickListener {
        fun onViewPagerClick(position: Int)

    }

    private var listener: OnViewPagerClickListener? = null
    open fun setOnViewPagerClickListener(l: OnViewPagerClickListener) {
        listener = l
    }

//    override fun getPageWidth(position: Int): Float {
//        return 0.8F
//    }


}