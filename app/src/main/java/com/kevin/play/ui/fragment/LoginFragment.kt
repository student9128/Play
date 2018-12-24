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

/**
 * Created by Kevin on 2018/12/23<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class LoginFragment : BaseFragment(), PersonContract.View {

    companion object {
        fun newInstance(args: String): BaseFragment {
            var fragment = LoginFragment()
            var bundle = Bundle()
            bundle.putString("args", args)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var etUsername: EditText? = null
    private var etPassword: EditText? = null
    private var tvLogin: TextView? = null
    private var tvRegister: TextView? = null
    private var listener: OnRegisterListener? = null
    private var mPresenter: PersonContract.Presenter? = null

    open interface OnRegisterListener {
        fun onRegisterClick()
        fun loginSuccessful(d:LoginData)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            listener = context as OnRegisterListener

        } catch (e: Throwable) {
            toast(context.toString() + "must implement OnRegisterListener")
        }
    }

    override fun setLayoutResId(): Int {
        return R.layout.fragment_login
    }

    override fun initView() {
        mView?.let {
            etUsername = it.findViewById(R.id.et_user_name)
            etPassword = it.findViewById(R.id.et_password)
            tvLogin = it.findViewById(R.id.tv_login)
            tvRegister = it.findViewById(R.id.tv_register)
        }
        mPresenter = PersonPresenter(this, RequestDataSource.getSingleInstance()!!)
    }

    override fun initListener() {
        tvLogin!!.setOnClickListener {
            val username = etUsername!!.text.toString().trim()
            val password = etPassword!!.text.toString().trim()
            when {
                username.isNullOrEmpty() -> {
                    toast("账号不能为空")
                }
                password.isNullOrEmpty() -> {
                    toast("密码不能为空")
                }
                else -> {
                    mPresenter!!.login(username, password)
                }
            }
        }
        tvRegister!!.setOnClickListener { listener!!.onRegisterClick() }
    }

    override fun loadData() {
        verifyState(tempData)
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

    override fun loginSuccessful(d:LoginData) {
        toast("登录成功")
        listener!!.loginSuccessful(d)
    }

    override fun resisterSuccessful() {

    }

    override fun loginFailed(msg: String?) {
        toast(if (msg != null) msg!! else "登录失败")
    }

    override fun resisterFailed(msg: String?) {
    }
}