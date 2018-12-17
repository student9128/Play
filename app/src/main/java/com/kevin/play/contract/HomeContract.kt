package com.kevin.play.contract

import com.kevin.play.base.BasePresenter
import com.kevin.play.base.BaseView
import com.kevin.play.bean.HomeBannerData

/**
 * Created by Kevin on 2018/12/6<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
open class HomeContract {
    open interface Presenter : BasePresenter {
        /**
         * get data about banner.
         */
        fun requestDataBanner()
    }

    open interface View : BaseView<Presenter> {
        /**
         * show data
         */
        fun showDataBanner(data:List<HomeBannerData>)

        fun showTips(msg:String)
    }
}