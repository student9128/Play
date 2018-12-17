package com.kevin.play.base

import android.os.Bundle
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

    abstract fun initListener()

    open abstract fun initView()

    open abstract fun setLayoutResId(): Int


}