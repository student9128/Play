package com.kevin.play.presenter

import com.kevin.play.bean.NavBean
import com.kevin.play.bean.NavData
import com.kevin.play.contract.NavContract
import com.kevin.play.data.RequestDataSource
import com.kevin.play.http.HttpCallback

/**
 * Created by Kevin on 2018/12/20<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class NavPresenter(private var view: NavContract.View, private var requestDataSource: RequestDataSource) : NavContract.Presenter {
    override fun requestDataNav() {
        val observable = requestDataSource.requestDataNav()
        var x = object : HttpCallback<NavBean>() {
            override fun onSuccess(response: NavBean) {
                val data = response.data
                view.showNav(data)
            }

            override fun onFailure(e: Throwable) {
                view.showError(null)
                view.showFailure("requestDataNav", e)
            }

        }
        x.request(observable)

    }
}