package com.kevin.play.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.view.ViewPager
import android.view.MotionEvent
import android.view.View
import android.widget.ExpandableListView
import android.widget.TextView
import com.kevin.play.R
import com.kevin.play.adapter.BannerAdapter
import com.kevin.play.adapter.NavAdapter
import com.kevin.play.base.BaseFragment
import com.kevin.play.bean.Content
import com.kevin.play.bean.HomeBannerData
import com.kevin.play.bean.NavData
import com.kevin.play.contract.HomeContract
import com.kevin.play.contract.NavContract
import com.kevin.play.data.RequestDataSource
import com.kevin.play.presenter.HomePresenter
import com.kevin.play.presenter.NavPresenter
import com.kevin.play.ui.activity.WebActivity
import com.kevin.play.util.ViewPagerUtils
import com.kevin.play.view.transformer.*
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_nav.*

/**
 * Created by Kevin on 2018/12/5<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class NavFragment : BaseFragment(), NavContract.View, NavAdapter.OnChildItemClickListener {

    private var mPresenter: NavContract.Presenter? = null
    private var data: MutableList<NavData> = ArrayList()
    private var adapter: NavAdapter? = null

    companion object {
        fun newInstance(args: String): BaseFragment {
            var fragment = NavFragment()
            var bundle = Bundle()
            bundle.putString("args", args)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun setLayoutResId(): Int {
        return R.layout.fragment_nav
    }

    override fun initView() {
        mPresenter = NavPresenter(this, RequestDataSource.getSingleInstance()!!)
        adapter = NavAdapter(context!!, data)
        var listView = mView!!.findViewById<ExpandableListView>(R.id.listView)
        listView.setAdapter(adapter)

    }

    override fun initListener() {
        adapter!!.setOnChildItemClickListener(this)
    }

    override fun loadData() {
        mPresenter!!.requestDataNav()
    }

    override fun showNav(d: List<NavData>) {
        adapter!!.updateData(d)
        verifyState(d)
    }

    override fun addDisposable(d: Disposable) {
        cDisposable.add(d)
    }

    override fun showError(data: List<Any>?) {
        verifyState(data)
    }

    override fun showFailure(string: String, e: Throwable) {
        showFailureException(string, e)
    }

    override fun setPresenter(presenter: NavContract.Presenter) {
        mPresenter = presenter
    }

    override fun onChildItemClick(groupPosition: Int, childPosition: Int) {
        val link = data[groupPosition].articles[childPosition].link
        var intent = Intent(mActivity!!, WebActivity::class.java)
        intent.putExtra("url", link)
        startActivity(intent)
    }


}