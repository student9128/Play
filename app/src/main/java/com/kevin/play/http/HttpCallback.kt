package com.kevin.play.http

import com.kevin.play.base.BaseObserve
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by Kevin on 2018/12/19<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
abstract class HttpCallback<T> {
    open fun request(observable: Observable<T>) {
        observable.subscribeOn(Schedulers.io())
            .doOnSubscribe { }
            .throttleFirst(1500, TimeUnit.MICROSECONDS)
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseObserve<T>() {
                override fun onSubscribe(d: Disposable) {
                    super.onSubscribe(d)
                }

                override fun onNext(t: T) {
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

    open abstract fun onSuccess(response: T)
    open abstract fun onFailure(e: Throwable)
}