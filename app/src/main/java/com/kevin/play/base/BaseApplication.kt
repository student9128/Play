package com.kevin.play.base

import android.app.Application
import android.content.Context
import com.kevin.play.util.SPUtils

/**
 * Created by Kevin on 2018/12/5<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class BaseApplication : Application() {
    companion object {

        var mContext: Context? = null
        open fun getContext(): Context {
            return mContext!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
        SPUtils.initSP(this)
    }


}