package com.kevin.play.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.view.ViewPager
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import com.kevin.play.R
import com.kevin.play.adapter.BannerAdapter
import com.kevin.play.base.BaseFragment
import com.kevin.play.bean.HomeBannerData
import com.kevin.play.constant.Constants
import com.kevin.play.contract.HomeContract
import com.kevin.play.data.RequestDataSource
import com.kevin.play.presenter.HomePresenter
import com.kevin.play.ui.activity.WebActivity
import com.kevin.play.util.DisplayUtils
import com.kevin.play.util.ViewPagerUtils
import com.kevin.play.view.transformer.*
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by Kevin on 2018/12/5<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class HomeFragment : BaseFragment(), HomeContract.View, ViewPager.OnPageChangeListener, View.OnTouchListener, BannerAdapter.OnViewPagerClickListener {

    var homeBanner: ViewPager? = null
    private var currentPosition = 1
    private var d: MutableList<HomeBannerData> = ArrayList()

    private var adapter: BannerAdapter? = null
    private var data = ArrayList<HomeBannerData>()
    private var mPresenter: HomeContract.Presenter? = null
    private var isPlay = false

    override fun addDisposable(d: Disposable) {
        cDisposable.add(d)
    }

    override fun showTips(msg: String) {
        toast(msg)
    }


    override fun setPresenter(presenter: HomeContract.Presenter) {
        mPresenter = presenter
    }

    companion object {
        open fun newInstance(args: String): HomeFragment {
            var fragment = HomeFragment()
            var bundle = Bundle()
            bundle.putString("args", args)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun setLayoutResId(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        mPresenter = HomePresenter(this, RequestDataSource.getSingleInstance()!!)
        var bundle = arguments
        val string = bundle!!.getString("args")
        var tvText = mView!!.findViewById<TextView>(R.id.tvText)
        tvText.text = string

        homeBanner = mView!!.findViewById<ViewPager>(R.id.homeBanner)
        adapter = BannerAdapter(activity!!.applicationContext, data)
        homeBanner?.let {
            it.adapter = adapter
            it.setPageTransformer(true, PagerTransformerZoomOut())
//            it.setPageTransformer(true, PagerTransformerSmooth())
//            it.setPageTransformer(true, PagerTransformer3D())
            ViewPagerUtils.setDuration(it.context, it)
            it.addOnPageChangeListener(this)
            it.setOnTouchListener(this)
        }

//        homeBanner.setPageTransformer(true, PagerTransformer3D())
//        homeBanner.setPageTransformer(true, PagerTransformerAlpha())
//        homeBanner.setPageTransformer(true, PagerTransformerCover())
//        homeBanner.setPageTransformer(true, PagerTransformer3DInner())

    }


    override fun initListener() {
        adapter!!.setOnViewPagerClickListener(this)
    }

    override fun loadData() {
        mPresenter!!.requestDataBanner()
    }

    override fun showDataBanner(data: List<HomeBannerData>) {
        adapter!!.addData(data)
        verifyState(data)
        homeBanner!!.setCurrentItem(1, false)
        if (!isPlay){
            startAutoPlay()
        }
    }

    var handler = Handler()
    var currentItem = 0
    private var runnable = object : Runnable {
        override fun run() {
            currentItem = homeBanner!!.currentItem
            currentItem++
            homeBanner!!.currentItem = currentItem
            handler.postDelayed(this,Constants.BANNER_INTERVAL_TIME)
        }

    }

    override fun onViewPagerClick(position: Int) {
        val bean = data[position]
        val url = bean.url
        var intent = Intent(mActivity, WebActivity::class.java)
        intent.putExtra("url", url)
        startActivity(intent)
    }


    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> stopAutoPlay()
            MotionEvent.ACTION_UP -> startAutoPlay()
        }
        return false
    }

    override fun onPageScrollStateChanged(state: Int) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            if (currentPosition == 0) {
                homeBanner!!.setCurrentItem(adapter!!.count - 2, false)
            }
            if (currentPosition == adapter!!.count - 1) {
                homeBanner!!.setCurrentItem(1, false)
            }
        }
    }

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
    }

    override fun onPageSelected(p0: Int) {
        currentPosition = p0
        printD("position=======$p0")
    }

    override fun showError(data: List<Any>?) {
        verifyState(data)
    }


    private fun startAutoPlay() {
        handler.postDelayed(runnable, Constants.BANNER_INTERVAL_TIME)
        isPlay = true
    }

    private fun stopAutoPlay() {
        handler.removeCallbacks(runnable)
        isPlay = false
    }

    override fun onPause() {
        super.onPause()
        if (isPlay){
        stopAutoPlay()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!isPlay){
        startAutoPlay()
        }
    }

}