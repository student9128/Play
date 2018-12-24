package com.kevin.play.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.support.v4.content.ContextCompat
import android.view.View
import com.kevin.play.R
import com.kevin.play.base.BaseActivity
import com.kevin.play.bean.LoginData
import com.kevin.play.ui.fragment.LoginFragment
import com.kevin.play.ui.fragment.RegisterFragment
import kotlinx.android.synthetic.main.layout_tool_bar.*

/**
 * Created by Kevin on 2018/12/23<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class LoginActivity : BaseActivity(), LoginFragment.OnRegisterListener, RegisterFragment.OnLoginListener {

    private var loginFragment: LoginFragment? = null
    private var registerFragment: RegisterFragment? = null
    override fun setLayoutResId(): Int {
        return R.layout.activity_login
    }

    override fun initView() {
        supportActionBar!!.title = getString(R.string.login)
        toolBar.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        toolBar.navigationIcon = getDrawable(R.drawable.ic_back_material)
        toolBar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        loginFragment = LoginFragment.newInstance("login") as LoginFragment
        supportFragmentManager.beginTransaction()
            .add(R.id.fl_content, loginFragment!!)
            .commit()

    }

    override fun initListener() {

    }

    override fun onRegisterClick() {
        supportActionBar!!.title = getString(R.string.register)
        if (registerFragment == null) {
            registerFragment = RegisterFragment()
        }
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.fragment_slide_from_right, R.anim.fragment_slide_to_left)
            .replace(R.id.fl_content, registerFragment!!)
            .commit()

    }


    override fun onLoginClick() {
        supportActionBar!!.title = getString(R.string.login)
        loginFragment?.let {
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fragment_slide_from_left, R.anim.fragment_slide_to_right)
                .replace(R.id.fl_content, loginFragment!!)
                .commit()

        }
    }

    override fun loginSuccessful(d: LoginData) {
        toast("登录成功")
        var intent = Intent()
        intent.putExtra("userInfo", d)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

}