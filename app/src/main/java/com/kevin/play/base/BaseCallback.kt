package com.kevin.play.base

import io.reactivex.disposables.Disposable

/**
 * Created by Kevin on 2018/12/13<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class BaseCallback<T> : BaseObserve<T>() {
    override fun onSubscribe(d: Disposable) {
        super.onSubscribe(d)
    }

    override fun onNext(t: T) {
        super.onNext(t)
    }

    override fun onComplete() {
        super.onComplete()
    }

    override fun onError(e: Throwable) {
        super.onError(e)
    }
}