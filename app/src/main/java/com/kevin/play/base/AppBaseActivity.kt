package com.kevin.play.base

import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.kevin.play.util.LogUtils
import com.kevin.play.util.ToastUtils

/**
 * Created by Kevin on 2018/12/5<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
open class AppBaseActivity : AppCompatActivity() {
    var TAG = javaClass.simpleName!!

    /**
     * toast with message
     * @param msg the message
     */
    open fun toast(msg: String) {
        ToastUtils.showToast(applicationContext, msg, Toast.LENGTH_SHORT)
    }

    /**
     * toast with resId
     * @param resId the message's res id
     */
    open fun toast(resId: Int) {
        ToastUtils.showToast(applicationContext, resId, Toast.LENGTH_SHORT)
    }

    /**
     * toast with variable arguments
     * @param format format of arguments
     * @param args the arguments
     */
    open fun toast(format: String, vararg args: Any) {
        ToastUtils.showToast(applicationContext, String.format(format, args), Toast.LENGTH_SHORT)
    }


    open fun toastLong(msg: String) {
        ToastUtils.showToast(applicationContext, msg, Toast.LENGTH_LONG)
    }


    open fun toastLong(resId: Int) {
        ToastUtils.showToast(applicationContext, resId, Toast.LENGTH_LONG)
    }


    open fun printD(msg: String) {
        LogUtils.log("d", TAG, msg)
    }

    open fun printI(msg: String) {
        LogUtils.log("i", TAG, msg)
    }

    open fun printW(msg: String) {
        LogUtils.log("w", TAG, msg)
    }

    open fun printE(msg: String) {
        LogUtils.log("e", TAG, msg)
    }

    open fun printV(msg: String) {
        LogUtils.log("v", TAG, msg)
    }

    open fun printD(format: String, vararg msg: Any?) {
        LogUtils.log("d", TAG, format, msg)
    }

    open fun printI(format: String, vararg msg: Any?) {
        LogUtils.log("i", TAG, format, msg)
    }

    open fun printW(format: String, vararg msg: Any?) {
        LogUtils.log("w", TAG, format, msg)
    }

    open fun printE(format: String, vararg msg: Any?) {
        LogUtils.log("e", TAG, format, msg)
    }

    open fun printV(format: String, vararg msg: Any?) {
        LogUtils.log("v", TAG, format, msg)
    }

}
