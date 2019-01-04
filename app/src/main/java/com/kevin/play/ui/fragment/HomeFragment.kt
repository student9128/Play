package com.kevin.play.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MotionEvent
import android.view.View
import com.kevin.play.R
import com.kevin.play.adapter.BannerAdapter
import com.kevin.play.adapter.BannerAdapterJava
import com.kevin.play.adapter.HomeArticleAdapter
import com.kevin.play.base.BaseFragment
import com.kevin.play.bean.Content
import com.kevin.play.bean.HomeBannerData
import com.kevin.play.constant.Constants
import com.kevin.play.contract.HomeContract
import com.kevin.play.data.RequestDataSource
import com.kevin.play.presenter.HomePresenter
import com.kevin.play.ui.activity.WebActivity
import com.kevin.play.util.ViewPagerUtils
import com.kevin.play.view.DividerItemDecoration
import com.kevin.play.view.transformer.*
import io.reactivex.disposables.Disposable

/**
 * Created by Kevin on 2018/12/5<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class HomeFragment : BaseFragment(), HomeContract.View, ViewPager.OnPageChangeListener, View.OnTouchListener, BannerAdapterJava.OnViewPagerClickListener, HomeArticleAdapter.OnLoadMoreListener, HomeArticleAdapter.OnChildItemClickListener {

    var homeBanner: ViewPager? = null
    var recyclerView: RecyclerView? = null
    private var currentPosition = 1
    private var d: MutableList<HomeBannerData> = ArrayList()

    private var adapter: BannerAdapterJava? = null
    private var data = ArrayList<HomeBannerData>()
    private var mPresenter: HomeContract.Presenter? = null
    private var isPlay = false
    private var homeAdapter: HomeArticleAdapter? = null
    private var articleData = ArrayList<Content>()
    private var pageNum = 0
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
        fun newInstance(args: String): HomeFragment {
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
        homeBanner = mView!!.findViewById<ViewPager>(R.id.homeBanner)
        adapter = BannerAdapterJava(activity!!.applicationContext, data)
        homeBanner?.let {
            it.adapter = adapter
            it.setPageTransformer(true, PagerTransformerZoomOut())
            ViewPagerUtils.setDuration(it.context, it)
            it.addOnPageChangeListener(this)
            it.setOnTouchListener(this)
        }
        homeAdapter = HomeArticleAdapter(context!!, articleData)
        var l = LinearLayoutManager(mActivity)
        var divider = DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL_LIST)
        divider.setDivider(R.drawable.bg_recycler_divider)
        recyclerView = mView!!.findViewById(R.id.recyclerView)
        recyclerView?.let {
            it.addItemDecoration(divider)
            it.layoutManager = l
            it.adapter = homeAdapter
        }

    }


    override fun initListener() {
        adapter!!.setOnViewPagerClickListener(this)
        homeAdapter!!.setOnLoadMoreListener(this)
        homeAdapter!!.setOnChildItemClickListener(this)
    }

    override fun loadData() {
        mPresenter!!.requestDataBanner()
        mPresenter!!.requestArticleList(0, Constants.REQUEST_REFRESH)
    }

    fun refreshData() {
        if (mPresenter == null) {
            mPresenter = HomePresenter(this, RequestDataSource.getSingleInstance()!!)
        }
        homeAdapter = HomeArticleAdapter(activity!!, articleData)
        adapter = BannerAdapterJava(activity!!.applicationContext, data)
        mPresenter!!.requestDataBanner()
        mPresenter!!.requestArticleList(0, Constants.REQUEST_REFRESH)
        stopAutoPlay()
    }

    override fun showDataBanner(data: List<HomeBannerData>) {
        adapter?.addData(data)
        verifyState(data)
        homeBanner?.setCurrentItem(1, false)
        if (!isPlay) {
            startAutoPlay()
        }
    }

    override fun showArticleList(data: List<Content>, type: String) {
        when (type) {
            Constants.REQUEST_REFRESH -> homeAdapter?.updateData(data)
            Constants.REQUEST_LOAD_MORE -> homeAdapter?.addData(data)
        }
    }

    var handler = Handler()
    var currentItem = 0
    private var runnable = object : Runnable {
        override fun run() {
            currentItem = homeBanner!!.currentItem
            currentItem++
            homeBanner!!.currentItem = currentItem
            handler.postDelayed(this, Constants.BANNER_INTERVAL_TIME)
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
//        printD("position=======$p0")
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
        if (isPlay) {
            stopAutoPlay()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!isPlay) {
            startAutoPlay()
        }
    }

    override fun onChildItemClick(viewId: Int, position: Int) {
        when (viewId) {
            R.id.iv_favorite -> {
                isLogin = getBooleanSP(Constants.KEY_LOGIN_STATE)
                if (isLogin) {
                    val content = articleData[position]
                    val id = content.id
                    val collect = content.collect
                    if (collect) {
                        mPresenter!!.requestUnCollectArticle(id, position)
                    } else {
                        mPresenter!!.requestCollectArticle(id, position)
                    }
                } else {
                    toast(getString(R.string.login_tip))
                }
//                printW("position------$position")
            }
            R.id.tv_title -> {
                val link = articleData[position].link
                var intent = Intent(mActivity, WebActivity::class.java)
                intent.putExtra("url", link)
                startActivity(intent)

            }
        }
    }

    override fun onLoadMore() {
        mPresenter!!.requestArticleList(++pageNum, Constants.REQUEST_LOAD_MORE)
    }

    override fun showFailure(string: String, e: Throwable) {
        showFailureException(string, e)
    }

    override fun notifyCollectItem(position: Int) {
        val content = articleData[position]
        val collect = content.collect
        content.collect = !collect
        homeAdapter!!.setData(position, content)

    }


}