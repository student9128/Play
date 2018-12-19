package com.kevin.play.contract

import com.kevin.play.base.BasePresenter
import com.kevin.play.base.BaseView
import com.kevin.play.bean.Content
import com.kevin.play.bean.HomeBannerData

/**
 * Created by Kevin on 2018/12/6<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
open class HomeContract {
    interface Presenter : BasePresenter {
        /**
         * get data about banner.
         */
        fun requestDataBanner()

        fun requestArticleList(page: Int,type: String)
    }

    interface View : BaseView<Presenter> {
        /**
         * show data
         */
        fun showDataBanner(data: List<HomeBannerData>)

        fun showArticleList(data: List<Content>,type:String)

        fun showTips(msg: String)
    }
}