package com.kevin.play.ui.activity

import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.ConnectivityManager
import android.net.NetworkRequest
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kevin.play.MainActivity
import com.kevin.play.R
import com.kevin.play.base.AppBaseActivity
import com.kevin.play.bean.Splash
import com.kevin.play.constant.Constants
import com.kevin.play.http.AppRetrofit.Companion.appRetrofit
import com.kevin.play.http.HttpCallback
import com.kevin.play.manager.PermissionManager
import com.kevin.play.util.NetUtils
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * Created by Kevin on 2018/12/17<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class SplashActivity : AppBaseActivity() {
    private var cm: ConnectivityManager? = null
    var callback: ConnectivityManager.NetworkCallback = NetUtils.callback
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)
        cm = NetUtils.initCM(this)
        cm!!.registerNetworkCallback(NetworkRequest.Builder().build(), callback)
        var ivSplash = findViewById<ImageView>(R.id.iv_splash)
        sfSplash.visibility = View.GONE
        if (!this.isTaskRoot) {
            var action = intent.action
            action?.let {
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN == action) {
                    finish()
                    return
                }
            }

        }

        if (NetUtils.isNetworkAvailable()) {
            requestWallpaper(ivSplash)
        } else {
            showTime()
        }

    }

    private fun requestWallpaper(ivSplash: ImageView) {
        val httpService = appRetrofit.getHttpService()
        val observable = httpService.getBingWallpaper()
        var x = object : HttpCallback<Splash>() {
            override fun onSuccess(response: Splash) {
                val images = response.images
                if (images.isNotEmpty()) {
                    Glide.with(this@SplashActivity)
                        .load("https://cn.bing.com" + images[0].url)
                        .apply(RequestOptions().centerCrop())
                        .into(ivSplash)
                    sfSplash.visibility = View.VISIBLE
                    showTime()
                }
            }

            override fun onFailure(e: Throwable) {
                finish()
            }


        }
        x.request(observable)
    }

    private fun showTime() {
        Handler().postDelayed({
            val animation = AnimationUtils.loadAnimation(this@SplashActivity, R.anim.animation_fadeout)
            animation.fillAfter = true
            window.setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()

        }, 3000)
    }

    override fun onDestroy() {
        super.onDestroy()
        cm!!.unregisterNetworkCallback(callback)
    }
}