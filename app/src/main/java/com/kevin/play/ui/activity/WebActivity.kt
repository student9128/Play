package com.kevin.play.ui.activity

import android.content.Context
import android.view.KeyEvent
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.kevin.play.R
import com.kevin.play.base.BaseActivity
import kotlinx.android.synthetic.main.activity_web.*

/**
 * Created by Kevin on 2018/12/18<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class WebActivity : BaseActivity(), View.OnKeyListener {

    private var url = ""
    override fun setLayoutResId(): Int {
        return R.layout.activity_web
    }

    override fun initView() {
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.useWideViewPort = true
        webSettings.setSupportZoom(true)
        webSettings.builtInZoomControls = true
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        webView.webViewClient = webViewClient
        webView.webChromeClient = webChromeClient

        url = intent.getStringExtra("url")
        webView.loadUrl(url)

    }

    private val webViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            view!!.loadUrl(request!!.url.toString())
            return true
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            supportActionBar!!.title = view!!.title
        }

    }
    private val webChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            progressView.visibility = View.VISIBLE
            if (newProgress>50){
            progressView.alpha = (50 - newProgress/2) / 25.0F
            }
            progressView.setmProgress(newProgress)

//            progressBarView.visibility = View.VISIBLE
//            if (newProgress>50){
//            progressBarView.alpha = (50-newProgress/2)/50f
//            }
//            progressBarView.setProgress(newProgress)
        }
    }

    override fun initListener() {
        webView.setOnKeyListener(this)
    }

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack()
            true
        } else {
            false
        }
    }


}