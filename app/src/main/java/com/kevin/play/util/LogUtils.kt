package com.kevin.play.util

import android.util.Log
import com.kevin.play.BuildConfig

/**
 * Created by Kevin on 2018/12/5<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
object LogUtils {
    var isDebug = BuildConfig.DEBUG
    open fun log(type: String, tag: String, msg: String) {
        if (isDebug) {
            if (msg.isNotEmpty()) {
                when (type) {
                    "d" -> {
                        Log.d("$tag--->", msg)
                    }
                    "i" -> {
                        Log.i("$tag--->", msg)
                    }
                    "e" -> {
                        Log.e("$tag--->", msg)
                    }
                    "w" -> {
                        Log.w("$tag--->", msg)

                    }
                    "v" -> {
                        Log.v("$tag--->", msg)
                    }
                }
            } else {
                when (type) {
                    "d" -> {
                        Log.d("$tag--->", "message is null with tag $tag")
                    }
                    "i" -> {
                        Log.i("$tag--->", "message is null with tag $tag")
                    }
                    "e" -> {
                        Log.e("$tag--->", "message is null with tag $tag")
                    }
                    "w" -> {
                        Log.w("$tag--->", "message is null with tag $tag")

                    }
                    "v" -> {
                        Log.v("$tag--->", "message is null with tag $tag")
                    }
                }
            }
        }
    }

    open fun log(type: String, tag: String, format: String, vararg msg: Any?) {
        if (isDebug) {
            log(type, tag, String.format(format, msg))
        }
    }

}