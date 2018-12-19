package com.kevin.play.http

import com.kevin.play.base.BaseObserve
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Kevin on 2018/12/19<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
abstract class HttpCallback {
    open fun request(observable: Observable<Map<String, Any>>) {
        observable.subscribeOn(Schedulers.io())
            .doOnSubscribe { }
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseObserve<Map<String, Any>>() {
                override fun onSubscribe(d: Disposable) {
                    super.onSubscribe(d)
                }

                override fun onNext(t: Map<String, Any>) {
                    super.onNext(t)
                    onSuccess(t)
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    onFailure(e)
                }

                override fun onComplete() {
                    super.onComplete()
                }

            })
    }

    open abstract fun onSuccess(response: Map<String, Any>)
    open abstract fun onFailure(e: Throwable)
}