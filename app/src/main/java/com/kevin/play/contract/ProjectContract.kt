package com.kevin.play.contract

import com.kevin.play.base.BasePresenter
import com.kevin.play.base.BaseView
import com.kevin.play.bean.ProjectList
import com.kevin.play.bean.ProjectTitle
/**
 * Created by Kevin on 2018/12/20<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class ProjectContract {
    interface Presenter : BasePresenter {
        fun requestDataProject()
        fun requestDataProjectList(page: Int, cid: String,type:String)
    }

    interface View : BaseView<Presenter> {
        fun showProjectTree(d: List<ProjectTitle>)
        fun showProjectList(d: List<ProjectList>,type: String)
    }
}