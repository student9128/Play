package com.kevin.play.base

import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Kevin on 2018/12/6<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
open class BaseObserve<T> : Observer<T> {
    var cDisposable = CompositeDisposable()
    override fun onComplete() {
    }

    override fun onSubscribe(d: Disposable) {
        cDisposable.add(d)

    }

    override fun onNext(t: T) {
    }

    override fun onError(e: Throwable) {
    }

}