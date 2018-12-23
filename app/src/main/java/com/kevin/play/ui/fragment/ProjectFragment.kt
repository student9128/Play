package com.kevin.play.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.kevin.play.R
import com.kevin.play.adapter.ProjectListAdapter
import com.kevin.play.adapter.ProjectTreeAdapter
import com.kevin.play.base.BaseFragment
import com.kevin.play.base.BaseRecyclerViewAdapter
import com.kevin.play.bean.ProjectList
import com.kevin.play.bean.ProjectTitle
import com.kevin.play.constant.Constants
import com.kevin.play.contract.ProjectContract
import com.kevin.play.data.RequestDataSource
import com.kevin.play.presenter.ProjectPresenter
import com.kevin.play.view.DividerItemDecoration
import io.reactivex.disposables.Disposable

/**
 * Created by Kevin on 2018/12/20<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class ProjectFragment : BaseFragment(), ProjectContract.View, BaseRecyclerViewAdapter.OnLoadMoreListener {

    private var mPresenter: ProjectContract.Presenter? = null
    private var treeAdapter: ProjectTreeAdapter? = null
    private var listAdapter: ProjectListAdapter? = null
    private var treeData: MutableList<ProjectTitle> = ArrayList()
    private var listData: MutableList<ProjectList> = ArrayList()
    var pageNum: Int = 1
    var cid=""

    companion object {
        fun newInstance(args: String): BaseFragment {
            var fragment = ProjectFragment()
            var bundle = Bundle()
            bundle.putString("args", args)
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun setLayoutResId(): Int {
        return R.layout.fragment_project
    }

    override fun initView() {
        mPresenter = ProjectPresenter(this, RequestDataSource.getSingleInstance()!!)
        var recyclerViewTitle = mView!!.findViewById<RecyclerView>(R.id.recyclerView_title)
        var recyclerViewContent = mView!!.findViewById<RecyclerView>(R.id.recyclerView_content)
        var l = LinearLayoutManager(mActivity)
        var ll = LinearLayoutManager(mActivity)
        var divider = DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL_LIST)
        divider.setDivider(R.drawable.bg_recycler_divider_l)

        treeAdapter = ProjectTreeAdapter(mActivity!!, treeData)
        recyclerViewTitle.addItemDecoration(divider)
        recyclerViewTitle.layoutManager = l
        recyclerViewTitle.adapter = treeAdapter

        listAdapter = ProjectListAdapter(mActivity!!, listData)
        recyclerViewContent.addItemDecoration(divider)
        recyclerViewContent.layoutManager = ll
        recyclerViewContent.adapter = listAdapter
    }

    override fun initListener() {
        listAdapter!!.setOnLoadMoreListener(this)
        treeAdapter!!.setOnRecyclerItemClickListener(object : BaseRecyclerViewAdapter.OnRecyclerItemClickListener {
            override fun onRecyclerItemClick(position: Int) {
                val id = treeData[position].id
                cid = treeData[position].id.toString()
                mPresenter!!.requestDataProjectList(1, id.toString(), Constants.REQUEST_REFRESH)
                pageNum=1
            }

        })
        listAdapter!!.setOnRecyclerItemClickListener(object : BaseRecyclerViewAdapter.OnRecyclerItemClickListener {
            override fun onRecyclerItemClick(position: Int) {
            }

        })
    }

    override fun loadData() {
        mPresenter!!.requestDataProject()
    }

    override fun showProjectTree(d: List<ProjectTitle>) {
        treeAdapter!!.updateData(d,true)
//        treeAdapter!!.setShowFoot(true)
        verifyState(d)
        if (d.isNotEmpty()) {
            val id = d[0].id
            cid = id.toString()
            mPresenter!!.requestDataProjectList(1, id.toString(), Constants.REQUEST_REFRESH)
        }
    }

    override fun showProjectList(d: List<ProjectList>, type: String) {
        when (type) {
            Constants.REQUEST_REFRESH -> {
                listAdapter!!.updateData(d)
            }
            Constants.REQUEST_LOAD_MORE -> {
                listAdapter!!.addData(d)
                listAdapter!!.setShowFoot(true)
            }
        }
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

    override fun setPresenter(presenter: ProjectContract.Presenter) {
        mPresenter = presenter
    }

    override fun onLoadMore() {
        mPresenter!!.requestDataProjectList(pageNum++, cid, Constants.REQUEST_LOAD_MORE)
    }

}