package com.kevin.play.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.kevin.play.R
import com.kevin.play.R.drawable.ic_place_holder_error
import com.kevin.play.manager.ThreadManager

/**
 * Created by Kevin on 2018/12/13<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
open abstract class CommonPage @JvmOverloads constructor(
    context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    companion object {
        const val STATE_UNLOADED = 0
        const val STATE_LOADING = 1
        const val STATE_ERROR = 2
        const val STATE_LOADED = 3
        const val STATE_EMPTY = 4
        const val STATE_NET_ERROR = 5

    }

    init {
        init()
    }

    private var mLoadingView: View? = null
    private var mErrorView: View? = null
    private var mSuccessView: View? = null
    private var mEmptyView: View? = null
    private var mNetErrorView: View? = null
    private var mState: Int = 0


    private fun init() {
        if (mSuccessView == null) {
            mSuccessView = initContentView()
            addPageView(mSuccessView!!)
        }
        if (mNetErrorView == null) {
            mNetErrorView = initNetErrorView()
            addPageView(mNetErrorView!!)
        }
        if (mLoadingView == null) {
            mLoadingView = initLoadingView()
            addPageView(mLoadingView!!)
        }
        if (mErrorView == null) {
            mErrorView = initErrorView(R.drawable.ic_place_holder_error, "请求失败,点击重试")
            addPageView(mErrorView!!)
        }
        if (mEmptyView == null) {
            mEmptyView = initEmptyView()
            addPageView(mEmptyView!!)
        }
        mSuccessView!!.visibility = View.GONE
        mLoadingView!!.visibility = View.VISIBLE
        mErrorView!!.visibility = View.GONE
        mEmptyView!!.visibility = View.GONE
        mNetErrorView!!.visibility = View.GONE

    }


    open fun initLoadingView(): View? {
        return View.inflate(context, R.layout.layout_loading_page, null)
    }

    open fun initErrorView(resId: Int?, text: String?): View? {
        var v = View.inflate(context, R.layout.layout_error_page, null)
        var tvError = v.findViewById<TextView>(R.id.tv_error)
        var ivError = v.findViewById<ImageView>(R.id.iv_error)
        if (resId != null) {
            ivError.setImageResource(resId)
        } else {
            ivError.setImageResource(ic_place_holder_error)
        }
        tvError.text = text
        tvError!!.setOnClickListener { show() }
        return v
    }

    open fun initNetErrorView(): View? {
        return initErrorView(R.drawable.ic_place_holder_error, context.getString(R.string.page_network_error))
    }

    open fun initEmptyView(): View? {
        return View.inflate(context, R.layout.layout_empty_page, null)
    }

    private fun addPageView(v: View) {
        this.addView(v, LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
    }

    open fun showPageView(mState: Int) {

        mLoadingView!!.visibility = if (mState == STATE_UNLOADED || mState == STATE_LOADING) View.VISIBLE else View.GONE
        mSuccessView!!.visibility = if (mState == STATE_LOADED) View.VISIBLE else View.GONE
        mErrorView!!.visibility = if (mState == STATE_ERROR) View.VISIBLE else View.GONE
        mEmptyView!!.visibility = if (mState == STATE_EMPTY) View.VISIBLE else View.GONE
        mNetErrorView!!.visibility = if (mState == STATE_NET_ERROR) View.VISIBLE else View.GONE

    }

    open fun show() {
        if (mState == STATE_UNLOADED || mState == STATE_ERROR || mState == STATE_NET_ERROR) {
            mState = STATE_LOADING
        }
        showPageView(mState)//点击后先showLoading
        reLoad()

    }

    /**
     * 获取网络请求后的状态
     */
    private fun getRequestStateAndShow() {
        ThreadManager.runOnSubThread(Runnable {
            //            var result = reLoad()
//            showPostResultPage(mState)
        })
    }

    /**
     * 展示请求后的页面
     */
    private fun showPostResultPage(mState: Int) {
        ThreadManager.runOnUIThread(Runnable { showPageView(mState) })
    }

    /**
     * 初始化界面上要显示的view
     */
    open abstract fun initContentView(): View?

    /**
     * 请求出现错误的时候点击重试
     */
    open abstract fun reLoad()


}