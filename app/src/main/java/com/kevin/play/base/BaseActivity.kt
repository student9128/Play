package com.kevin.play.base

import android.os.Bundle
import android.view.MenuItem
import com.kevin.play.R
import kotlinx.android.synthetic.main.layout_tool_bar.*

/**
 * Created by Kevin on 2018/12/5<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
abstract class BaseActivity : AppBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayoutResId())
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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

}