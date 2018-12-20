package com.kevin.play.contract

import com.kevin.play.base.BasePresenter
import com.kevin.play.base.BaseView
import com.kevin.play.bean.NavData

/**
 * Created by Kevin on 2018/12/20<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class NavContract {
    interface Presenter : BasePresenter {
        fun requestDataNav()
    }

    interface View : BaseView<Presenter> {
        fun showNav(d:List<NavData>)
    }
}