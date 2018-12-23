package com.kevin.play.presenter

import com.kevin.play.base.BaseObserve
import com.kevin.play.bean.ProjectBean
import com.kevin.play.bean.ProjectListBean
import com.kevin.play.bean.ProjectTitle
import com.kevin.play.contract.ProjectContract
import com.kevin.play.data.RequestDataSource
import com.kevin.play.http.HttpCallback
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
                    val projectList = t.data.projectList
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
}