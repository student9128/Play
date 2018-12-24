package com.kevin.play.contract

import com.kevin.play.base.BasePresenter
import com.kevin.play.base.BaseView
import com.kevin.play.bean.LoginBean
import com.kevin.play.bean.LoginData

/**
 * Created by Kevin on 2018/12/23<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class PersonContract {

    interface Presenter : BasePresenter {
        fun login(username: String, password: String)
        fun register(username: String, password: String, rePassword: String)
        fun logout()

    }

    interface View : BaseView<Presenter> {
        fun loginSuccessful(d:LoginData)
        fun loginFailed(msg:String?)
        fun resisterSuccessful()
        fun resisterFailed(msg:String?)
    }
}