package com.kevin.play.ui.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.*
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
    private lateinit var clipboard: ClipboardManager
    private var hasClipListen = false
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
        webSettings.displayZoomControls = false
        webSettings.loadWithOverviewMode = true
        webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        webSettings.databaseEnabled = true
        webSettings.cacheMode = WebSettings.LOAD_DEFAULT
        webSettings.setAppCacheEnabled(true)
//        webSettings.setAppCachePath()
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        webView.webViewClient = webViewClient
        webView.webChromeClient = webChromeClient

        url = intent.getStringExtra("url")
        webView.loadUrl(url)

        clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        registerPrimaryClipListener()

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
//            progressView.visibility = View.VISIBLE
//            if (newProgress > 50) {
//                progressView.alpha = (50 - newProgress / 2) / 25.0F
//            }
//            progressView.setmProgress(newProgress)

            progressBarView.visibility = View.VISIBLE
            if (newProgress > 50) {
                progressBarView.alpha = (50 - newProgress / 2) / 50f
            }
            progressBarView.setProgress(newProgress)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_web_view, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.action_open_in_browser -> {
                var intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
            R.id.action_copy_url -> copyUrl()

        }
        return true
    }

    private fun copyUrl() {
        val clipData = ClipData.newPlainText("Copied Text", url)
        clipboard.primaryClip = clipData

    }

    override fun onDestroy() {
        super.onDestroy()
        unRegisterPrimaryClipListener()
    }

    private var clipListener = object : ClipboardManager.OnPrimaryClipChangedListener {
        override fun onPrimaryClipChanged() {
            if (clipboard.hasPrimaryClip() && clipboard.primaryClip.itemCount > 0) {
                val text = clipboard.primaryClip.getItemAt(0).text
                if (!text.isNullOrEmpty()) {
                    toast("复制成功")
                }
            }
        }
    }

    private fun registerPrimaryClipListener() {
        if (!hasClipListen) {
            clipboard.addPrimaryClipChangedListener(clipListener)
            hasClipListen = true
        }

    }

    private fun unRegisterPrimaryClipListener() {
        if (hasClipListen) {
            clipboard.removePrimaryClipChangedListener(clipListener)
            hasClipListen = false
        }

    }

}