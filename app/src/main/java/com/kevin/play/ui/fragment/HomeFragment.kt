package com.kevin.play.ui.fragment

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.widget.TextView
import com.kevin.play.R
import com.kevin.play.adapter.BannerAdapter
import com.kevin.play.base.BaseFragment
import com.kevin.play.bean.HomeBannerData
import com.kevin.play.contract.HomeContract
import com.kevin.play.data.RequestDataSource
import com.kevin.play.presenter.HomePresenter
import com.kevin.play.util.ViewPagerUtils
import com.kevin.play.view.transformer.*
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by Kevin on 2018/12/5<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class HomeFragment : BaseFragment(), HomeContract.View, ViewPager.OnPageChangeListener {
    private var currentPosition = 1
    override fun onPageScrollStateChanged(state: Int) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            if (currentPosition == 0) {
                homeBanner.setCurrentItem(adapter!!.count - 2, false)
            }
            if (currentPosition == adapter!!.count - 1) {
                homeBanner.setCurrentItem(1, false)
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

    private var d: MutableList<HomeBannerData> = ArrayList()

    private var adapter: BannerAdapter? = null
    private var data = ArrayList<HomeBannerData>()
    override fun addDisposable(d: Disposable) {
        cDisposable.add(d)
    }

    private var mPresenter: HomeContract.Presenter? = null
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

        var homeBanner = mView!!.findViewById<ViewPager>(R.id.homeBanner)
        adapter = BannerAdapter(activity!!.applicationContext, data)
        homeBanner.adapter = adapter

//        homeBanner.setPageTransformer(true, PagerTransformer3D())
//        homeBanner.setPageTransformer(true, PagerTransformerAlpha())
//        homeBanner.setPageTransformer(true, PagerTransformerCover())
//        homeBanner.setPageTransformer(true, PagerTransformerSmooth())
//        homeBanner.setPageTransformer(true, PagerTransformer3DInner())
        homeBanner.setPageTransformer(true, PagerTransformerZoomOut())
        ViewPagerUtils.setDuration(homeBanner.context, homeBanner)
        homeBanner.addOnPageChangeListener(this)

    }


    override fun initListener() {
    }

    override fun loadData() {
        mPresenter!!.requestDataBanner()
    }

    override fun showDataBanner(data: List<HomeBannerData>) {
        adapter!!.addData(data)
        verifyState(data)
        homeBanner.setCurrentItem(1, false)
    }

}