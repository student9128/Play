package com.kevin.play.base

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Created by Kevin on 2018/12/14<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
abstract class Callback<T> : Observer<T> {
    override fun onComplete() {
    }

    override fun onSubscribe(d: Disposable) {
    }

    override fun onNext(t: T) {
    }

    override fun onError(e: Throwable) {
    }

    abstract fun onSuccess()
    abstract fun onFailure()
}