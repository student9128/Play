package com.kevin.play.base

import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkRequest
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import android.view.View
import com.kevin.play.R
import com.kevin.play.util.NetUtils
import com.kevin.play.view.dialog.CommonDialog
import kotlinx.android.synthetic.main.layout_tool_bar.*

/**
 * Created by Kevin on 2018/12/5<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
abstract class BaseActivity : AppBaseActivity() {
    private var cm: ConnectivityManager? = null
    var callback: ConnectivityManager.NetworkCallback = NetUtils.callback
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayoutResId())
        cm = NetUtils.initCM(this)
        cm!!.registerNetworkCallback(NetworkRequest.Builder().build(), callback)
        setSupportActionBar(toolBar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeButtonEnabled(true)
        }
        initView()
        initListener()
    }


    open abstract fun setLayoutResId(): Int
    open abstract fun initView()
    abstract fun initListener()

    open fun startAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.data = Uri.parse("package:$packageName")
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
//        cm!!.unregisterNetworkCallback(callback)
    }

    open fun showDialog(title: String, msg: String, btnLeft: String, btnRight: String, listener: BaseDialog.DialogButtonClickListener) {
        CommonDialog.newInstance(title, msg, btnLeft, btnRight, listener)
    }

    open fun showDialog(titleId: Int, msgId: Int, btnLeftId: Int, btnRightId: Int, listener: BaseDialog.DialogButtonClickListener) {
        var title = if (titleId == View.NO_ID || titleId == 0) null else getString(titleId)
        var msg = if (msgId == View.NO_ID || msgId == 0) null else getString(msgId)
        var btnLeft = if (btnLeftId == View.NO_ID || btnLeftId == 0) null else getString(btnLeftId)
        var btnRight = if (btnRightId == View.NO_ID || btnRightId == 0) null else getString(btnRightId)
        val commonDialog = CommonDialog.newInstance(title, msg, btnLeft, btnRight, listener)
        if (!commonDialog.isAdded) {
            commonDialog.show(supportFragmentManager, TAG)
        }
    }

    open fun showDialog(titleId: Int, msgId: Int, listener: BaseDialog.DialogButtonClickListener) {
        showDialog(titleId, msgId, R.string.dialog_btn_cancel, R.string.dialog_btn_ok, listener)
    }

}