package com.kevin.play.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network


/**
 * Created by Kevin on 2018/12/28<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
object NetUtils {
    var x = false
    var callback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network?) {
            super.onAvailable(network)
            x = true
        }

        override fun onLost(network: Network?) {
            super.onLost(network)
            x = false
        }

        override fun onUnavailable() {
            super.onUnavailable()
            x = false
        }
    }

    fun initCM(context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    fun isNetworkAvailable(): Boolean {
        return x
    }
}