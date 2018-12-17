package com.kevin.play.view

import android.content.Context
import android.database.DataSetObserver
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup

/**
 * Created by Kevin on 2018/12/17<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class InfiniteViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {
    var mAdapter: VPagerAdapter? = null
    private var l = MyOnPageChangeListener()
    private var v = VOPageChageListener()

    override fun setAdapter(adapter: PagerAdapter?) {
        mAdapter = VPagerAdapter(adapter)
        removeOnPageChangeListener(l)
        addOnPageChangeListener(l)
        super.setAdapter(adapter)
        currentItem = 1
    }

    override fun addOnPageChangeListener(listener: OnPageChangeListener) {
        super.addOnPageChangeListener(v)
    }

    override fun removeOnPageChangeListener(listener: OnPageChangeListener) {
        super.removeOnPageChangeListener(v)
    }

    inner class VOPageChageListener : OnPageChangeListener {
        private val listener: OnPageChangeListener? = null
        private var position: Int = 0
        override fun onPageScrollStateChanged(state: Int) {
            listener?.onPageScrollStateChanged(state)
            Log.w("InfiniteViewPager","position=$position")
            Log.d("InfiniteViewPager","adapterCount="+mAdapter!!.count)
            if (position == SCROLL_STATE_IDLE) {
                if (position == mAdapter!!.count - 1) {
                    setCurrentItem(1, false)
                } else if (position == 0) {
                    setCurrentItem(mAdapter!!.count - 2, false)
                }
            }
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            listener?.onPageScrolled(position, positionOffset, positionOffsetPixels)
        }

        override fun onPageSelected(position: Int) {
            this.position = position
            listener?.onPageSelected(position)
        }

    }

    inner class VPagerAdapter(private var adapter: PagerAdapter?) : PagerAdapter() {
        var p: Int = 0

        init {
            adapter!!.registerDataSetObserver(object : DataSetObserver() {
                override fun onChanged() {
                    notifyDataSetChanged()
                }

                override fun onInvalidated() {
                    notifyDataSetChanged()
                }
            })
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return adapter!!.isViewFromObject(view, `object`)
        }

        override fun getCount(): Int {
            return adapter!!.count + 2
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            p = position
            when (position) {
                0 -> p = adapter!!.count - 1
                adapter!!.count + 1 -> p = 0
                else -> p -= 1
            }
            return adapter!!.instantiateItem(container, p)
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            adapter!!.destroyItem(container, position, `object`)
        }

    }

    internal inner class MyOnPageChangeListener : ViewPager.OnPageChangeListener {
        var oldPosition = 0

        override fun onPageSelected(position: Int) {
            var position = position
            when (position) {
                0 -> position = mAdapter!!.count - 1
                mAdapter!!.count + 1 -> position = 0
                else -> // 控制 触发的方向
                    position += 1
            }
            currentItem = position
            oldPosition = position
        }

        override fun onPageScrollStateChanged(state: Int) {}

        override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}
    }
}