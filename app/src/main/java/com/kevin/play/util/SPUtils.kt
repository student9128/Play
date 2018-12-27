package com.kevin.play.util

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by Kevin on 2018/12/26<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
object SPUtils {
    lateinit var sp: SharedPreferences
    open fun initSP(context: Context) {
        sp = context.getSharedPreferences(context.packageName + "_preference", Context.MODE_PRIVATE)
    }

    fun getStringSP(key: String): String {
        return sp.getString(key, "")
    }

    fun setSP(key: String, value: String) {
        sp.edit().putString(key, value).commit()
    }

    fun getBooleanSP(key: String): Boolean {
        return sp.getBoolean(key, false)
    }

    fun setSP(key: String, value: Boolean) {
        sp.edit().putBoolean(key, value).commit()

    }

}