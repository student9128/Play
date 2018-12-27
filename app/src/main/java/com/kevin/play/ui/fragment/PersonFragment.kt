package com.kevin.play.ui.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.kevin.play.R
import com.kevin.play.base.BaseFragment
import com.kevin.play.bean.LoginData
import com.kevin.play.constant.Constants
import com.kevin.play.contract.PersonContract
import com.kevin.play.ui.activity.LoginActivity
import com.kevin.play.util.SPUtils
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * Created by Kevin on 2018/12/23<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class PersonFragment : BaseFragment() {
    private var mPresenter: PersonContract.Presenter? = null
    private var ivAvatar: ImageView? = null
    private var tvLogin: TextView? = null

    companion object {
        fun newInstance(args: String): BaseFragment {
            var fragment = PersonFragment()
            var bundle = Bundle()
            bundle.putString("args", args)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun setLayoutResId(): Int {
        return R.layout.fragment_person
    }

    override fun initView() {
        ivAvatar = mView!!.findViewById(R.id.iv_avatar)
        tvLogin = mView!!.findViewById(R.id.tv_login_state)
        val userName = getStringSP(Constants.KEY_USER_NAME)
        if (userName.isNotEmpty()) {
            tvLogin!!.text = userName
            tvLogin!!.isClickable = false
            ivAvatar!!.isClickable = false
        }
    }

    override fun initListener() {
        ivAvatar!!.setOnClickListener {
            var intent = Intent(mActivity, LoginActivity::class.java)
            startActivityForResult(intent, 0)
        }
    }

    override fun loadData() {
        verifyState(tempData)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (RESULT_OK == (resultCode)) {
            val data = data!!.getSerializableExtra("userInfo") as LoginData
            val username = data.username
            toast("$username 登录成功")
            tvLogin!!.text = username
            tvLogin!!.isClickable = false
            ivAvatar!!.isClickable = false
            SPUtils.setSP(Constants.KEY_USER_NAME, username)
            SPUtils.setSP(Constants.KEY_LOGIN_STATE, true)

        }
    }
}