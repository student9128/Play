package com.kevin.play.presenter

import android.content.Context
import com.google.gson.Gson
import com.kevin.play.base.BaseObserve
import com.kevin.play.bean.LoginBean
import com.kevin.play.contract.PersonContract
import com.kevin.play.data.RequestDataSource
import com.kevin.play.util.LogUtils
import com.kevin.play.util.ToastUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by Kevin on 2018/12/23<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class PersonPresenter(var view: PersonContract.View, private var requestDataSource: RequestDataSource) : PersonContract.Presenter {
    companion object {
        const val TAG = "PersonPresenter"
    }

    override fun login(username: String, password: String) {
        val observable = requestDataSource.requestLogin(username, password)
        observable.subscribeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .throttleFirst(1500, TimeUnit.MICROSECONDS)
            .subscribe(object : BaseObserve<Map<String, Any>>() {
                override fun onNext(t: Map<String, Any>) {
                    super.onNext(t)
                    val response = Gson().toJson(t)
                    LogUtils.d(TAG, "response=$response")
                    val bean = Gson().fromJson(response, LoginBean::class.java)
                    if (bean.errorCode != 0) {
                        view.loginFailed(bean.errorMsg)
                    } else {
                        view.loginSuccessful(bean.data)
                    }
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    LogUtils.e(TAG, "E=$e")
                }

                override fun onComplete() {
                    super.onComplete()
                }
            })
    }

    override fun register(username: String, password: String, rePassword: String) {
        val observable = requestDataSource.requestRegister(username, password, rePassword)
        observable.subscribeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .throttleFirst(1500, TimeUnit.MICROSECONDS)
            .subscribe(object : BaseObserve<Map<String, Any>>() {
                override fun onNext(t: Map<String, Any>) {
                    super.onNext(t)
                    val response = Gson().toJson(t)
                    LogUtils.d(TAG, "response=$response")
                    val bean = Gson().fromJson(response, LoginBean::class.java)
                    if (bean.errorCode != 0) {
                        view.resisterFailed(bean.errorMsg)
                    } else {
                        view.resisterSuccessful()
                    }

                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    LogUtils.e(TAG, "E=$e")
                }

                override fun onComplete() {
                    super.onComplete()
                }
            })
    }

    override fun logout() {
    }
}