package com.kevin.play.presenter

import android.util.Log
import com.google.gson.Gson
import com.kevin.play.base.BaseObserve
import com.kevin.play.bean.HomeBannerBean
import com.kevin.play.contract.HomeContract
import com.kevin.play.data.RequestDataSource
import io.reactivex.Observer

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Kevin on 2018/12/6<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
open class HomePresenter(view: HomeContract.View, private var requestDataSource: RequestDataSource) : HomeContract.Presenter {
    companion object {
        const val TAG = "HomePresenter"
    }

    var view = view
    var v: Disposable? = null
    override fun requestDataBanner() {
        val observable = requestDataSource.requestDataBanner()
        observable.subscribeOn(Schedulers.io())
            .doOnSubscribe { }
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseObserve<Map<String, Any>>() {
                override fun onNext(t: Map<String, Any>) {
                    super.onNext(t)
                    val response = Gson().toJson(t)
                    Log.d(TAG, "response==$response")
                    val bannerBean = Gson().fromJson(response, HomeBannerBean::class.java)
                    val data = bannerBean.data
                    view.showDataBanner(data)
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    view.showError(null)
                }

            })

    }
}