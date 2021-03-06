package com.kevin.play.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kevin.play.R
import com.kevin.play.constant.Constants
import com.kevin.play.util.NetUtils
import com.kevin.play.view.CommonPage
import com.kevin.play.view.dialog.CommonDialog
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Kevin on 2018/12/5<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
open abstract class BaseFragment : AppBaseFragment() {
    open var tempData: MutableList<String> = ArrayList()
    open var mView: View? = null
    private var isUIVisible = false
    private var isViewCreated = false
    var cDisposable = CompositeDisposable()
    private var commonPage: CommonPage? = null
    private var mState: Int = -2
    open var isLogin = getBooleanSP(Constants.KEY_LOGIN_STATE)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (commonPage == null) {
            commonPage = object : CommonPage(context) {
                override fun initContentView(): View? {
                    return showContentView(inflater, container)
                }

                override fun reLoad() {
                    return loadData()
                }

            }
        }
        tempData.add("temp")
        return commonPage
    }

    private fun showContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        mView = inflater.inflate(setLayoutResId(), container, false)
        initView()
        initListener()
        return mView!!
    }

    override fun onStart() {
        super.onStart()
        if (userVisibleHint) {
            isUIVisible = true
            lazyLoad()
        } else {
            isUIVisible = false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewCreated = true
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            isUIVisible = true
            lazyLoad()
        } else {
            isUIVisible = false
        }
    }

    private fun lazyLoad() {
        if (isViewCreated && isUIVisible) {
            loadData()
            isUIVisible = false
            isViewCreated = false
        }
    }

    open abstract fun setLayoutResId(): Int
    open abstract fun initView()
    open abstract fun initListener()
    open abstract fun loadData()


    /**
     * 校验页面状态
     */
    open fun verifyState(data: List<out Any>?) {
        mState = if (NetUtils.isNetworkAvailable()) {
            if (data == null) {
                CommonPage.STATE_ERROR
            } else {
                if (data.isEmpty()) {
                    CommonPage.STATE_EMPTY
                } else {
                    CommonPage.STATE_LOADED
                }
            }
        } else {
            CommonPage.STATE_NET_ERROR
        }
        commonPage?.showPageView(mState)
//        if (mState == -1) {
//            commonPage?.initErrorView(null, "暂网络连接")
//        }

    }

    fun showDialog(title: String, msg: String, btnLeft: String, btnRight: String, listener: BaseDialog.DialogButtonClickListener) {
        CommonDialog.newInstance(title, msg, btnLeft, btnRight, listener)
    }

    fun showDialog(titleId: Int, msgId: Int, btnLeftId: Int, btnRightId: Int, listener: BaseDialog.DialogButtonClickListener) {
        var title = if (titleId == View.NO_ID || titleId == 0) null else getString(titleId)
        var msg = if (titleId == View.NO_ID || titleId == 0) null else getString(msgId)
        var btnLeft = if (titleId == View.NO_ID || titleId == 0) null else getString(btnLeftId)
        var btnRight = if (titleId == View.NO_ID || titleId == 0) null else getString(btnRightId)
        CommonDialog.newInstance(title, msg, btnLeft, btnRight, listener)
    }

    fun showDialog(titleId: Int, msgId: Int, listener: BaseDialog.DialogButtonClickListener) {
        showDialog(titleId, msgId, R.string.dialog_btn_ok, R.string.dialog_btn_cancel, listener)
    }


}
