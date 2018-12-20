package com.kevin.play.base

import io.reactivex.disposables.Disposable

/**
 * Created by Kevin on 2018/12/6<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
open interface BaseView<T> : AppBaseView<T> {
    fun addDisposable(d: Disposable)
    fun showError(data: List<out Any>?)
    fun showFailure(string: String, e: Throwable)

}