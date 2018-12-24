package com.kevin.play.ui.fragment

import android.content.Context
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import com.kevin.play.R
import com.kevin.play.base.BaseFragment
import com.kevin.play.bean.LoginData
import com.kevin.play.contract.PersonContract
import com.kevin.play.data.RequestDataSource
import com.kevin.play.presenter.PersonPresenter
import io.reactivex.disposables.Disposable
import java.lang.Exception

/**
 * Created by Kevin on 2018/12/23<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class RegisterFragment : BaseFragment(), PersonContract.View {


    companion object {
        fun newInstance(args: String): BaseFragment {
            var fragment = RegisterFragment()
            var bundle = Bundle()
            bundle.putString("args", args)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var etUsername: EditText? = null
    private var etPassword: EditText? = null
    private var etRePassword: EditText? = null
    private var tvLogin: TextView? = null
    private var tvRegister: TextView? = null
    private var listener: OnLoginListener? = null
    private var mPresenter: PersonContract.Presenter? = null

    open interface OnLoginListener {
        fun onLoginClick()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            listener = context as OnLoginListener
        } catch (e: Throwable) {
            toast(context.toString() + "must implement OnLoginListener")
        }

    }

    override fun setLayoutResId(): Int {
        return R.layout.fragment_register
    }

    override fun initView() {
        mView?.let {
            etUsername = it.findViewById(R.id.et_user_name)
            etPassword = it.findViewById(R.id.et_password)
            etRePassword = it.findViewById(R.id.et_rePassword)
            tvLogin = it.findViewById(R.id.tv_login)
            tvRegister = it.findViewById(R.id.tv_register)
        }
        mPresenter = PersonPresenter(this, RequestDataSource.getSingleInstance()!!)
    }

    override fun initListener() {
        tvRegister!!.setOnClickListener {
            checkUserInfo()
        }
        tvLogin!!.setOnClickListener { listener!!.onLoginClick() }
    }

    override fun loadData() {
        verifyState(tempData)
    }

    private fun checkUserInfo() {
        val username = etUsername!!.text.toString().trim()
        val password = etPassword!!.text.toString().trim()
        val rePassword = etRePassword!!.text.toString().trim()
        when {
            username.isNullOrEmpty() -> {
                toast("账号不能为空")
            }
            password.isNullOrEmpty() -> {
                toast("密码不能为空")
            }
            rePassword.isNullOrEmpty() -> {
                toast("请确认密码")
            }
            password != rePassword -> {
                toast("两次输入密码不一致")
            }
            else -> {
                mPresenter!!.register(username, password, rePassword)
            }
        }
    }

    override fun loginSuccessful(d:LoginData) {
        toast("登录成功")
    }

    override fun resisterSuccessful() {
        toast("注册成功")
        listener!!.onLoginClick()
    }

    override fun addDisposable(d: Disposable) {
        cDisposable.add(d)
    }

    override fun showError(data: List<Any>?) {
    }

    override fun showFailure(string: String, e: Throwable) {
    }

    override fun setPresenter(presenter: PersonContract.Presenter) {
        mPresenter = presenter
    }

    override fun loginFailed(msg: String?) {
        toast(if (msg != null) msg!! else "登录失败")
    }

    override fun resisterFailed(msg: String?) {
        toast(if (msg != null) msg!! else "登录失败")
    }
}