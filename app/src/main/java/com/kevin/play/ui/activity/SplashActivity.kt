package com.kevin.play.ui.activity

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AnimationUtils
import com.kevin.play.MainActivity
import com.kevin.play.R
import com.kevin.play.base.AppBaseActivity
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * Created by Kevin on 2018/12/17<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class SplashActivity : AppBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)
        if (!this.isTaskRoot) {
            var action = intent.action
            action?.let {
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN == action) {
                    finish()
                    return
                }
            }

        }
        sfSplash.startShimmer()
        Handler().postDelayed({
            val animation = AnimationUtils.loadAnimation(this, R.anim.animation_fadeout)
            animation.fillAfter = true
            window.setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
            startActivity(Intent(this, MainActivity::class.java))
            if (sfSplash.isShimmerStarted) {
                sfSplash.stopShimmer()
            }
            finish()

        }, 3000)
    }
}