package com.kevin.play.base

import android.app.Application
import android.content.ComponentCallbacks2
import android.content.Context
import android.content.res.Configuration
import com.bumptech.glide.Glide
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
        this.registerComponentCallbacks(callback2)
    }

    var callback2 = object : ComponentCallbacks2 {
        override fun onLowMemory() {
            Glide.get(this@BaseApplication).clearMemory()
        }

        override fun onConfigurationChanged(newConfig: Configuration?) {
        }

        override fun onTrimMemory(level: Int) {
            if (level== ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN){
                Glide.get(this@BaseApplication).clearMemory()
            }
            Glide.get(this@BaseApplication).trimMemory(level)
        }

    }


}