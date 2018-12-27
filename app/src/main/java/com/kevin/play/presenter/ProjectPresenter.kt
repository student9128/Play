package com.kevin.play.presenter

import com.google.gson.Gson
import com.kevin.play.base.BaseObserve
import com.kevin.play.bean.ProjectBean
import com.kevin.play.bean.ProjectListBean
import com.kevin.play.contract.ProjectContract
import com.kevin.play.data.RequestDataSource
import com.kevin.play.http.HttpCallback
import com.kevin.play.util.LogUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Kevin on 2018/12/20<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class ProjectPresenter(var view: ProjectContract.View, private var requestDataSource: RequestDataSource) : ProjectContract.Presenter {
    override fun requestDataProject() {
        val observable = requestDataSource.requestProjectTree()

        var x = object : HttpCallback<ProjectBean>() {
            override fun onSuccess(response: ProjectBean) {
                val data = response.data
                view.showProjectTree(data)
                LogUtils.d("Project","ProjectTree")

            }

            override fun onFailure(e: Throwable) {
                view.showError(null)
                view.showFailure("requestDataProject", e)
            }

        }
        x.request(observable)
    }

    override fun requestDataProjectList(page: Int, cid: String, type: String) {
        val observable = requestDataSource.requestProjectList(page, cid)
        observable.subscribeOn(Schedulers.io())
            .doOnSubscribe { }
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseObserve<ProjectListBean>() {
                override fun onNext(t: ProjectListBean) {
                    super.onNext(t)
                    LogUtils.d("Project","ProjectList")
                    val projectList = t.data.projectList
                    LogUtils.d("Project","ProjectList.size="+projectList.size+"=type="+type)

                    view.showProjectList(projectList, type)
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    view.showFailure("requestDataProjectList", e)
                }

                override fun onComplete() {
                    super.onComplete()
                }
            })
    }
    override fun requestCollectArticle(id: Int,position:Int) {
        val observable = requestDataSource.requestCollectArticle(id)
        var x = object :HttpCallback<Map<String,Any>>(){
            override fun onSuccess(response: Map<String, Any>) {
                val r = Gson().toJson(response)
                view.notifyCollectItem(position)
                view.showTips("收藏成功")

            }

            override fun onFailure(e: Throwable) {
            }

        }
        x.request(observable)
    }

    override fun requestUnCollectArticle(id: Int,position:Int) {
        val observable = requestDataSource.requestUnCollectArticle(id)
        var x = object :HttpCallback<Map<String,Any>>(){
            override fun onSuccess(response: Map<String, Any>) {
                val r = Gson().toJson(response)
                view.notifyCollectItem(position)
                view.showTips("取消收藏成功")
            }

            override fun onFailure(e: Throwable) {
            }
        }
        x.request(observable)
    }

}