package com.kevin.play.data

import com.kevin.play.bean.NavBean
import com.kevin.play.bean.NavData
import com.kevin.play.bean.ProjectBean
import com.kevin.play.bean.ProjectListBean
import io.reactivex.Observable

/**
 * Created by Kevin on 2018/12/6<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
open interface RemoteDataSource {
    /**
     * request banner
     */
    fun requestDataBanner(): Observable<Map<String, Any>>

    fun requestHomeArticleList(page: Int): Observable<Map<String, Any>>

    fun requestDataNav(): Observable<NavBean>

    fun requestProjectTree(): Observable<ProjectBean>

    fun requestProjectList(page: Int, cid: String): Observable<ProjectListBean>

    fun requestLogin(username: String, password: String): Observable<Map<String, Any>>

    fun requestRegister(username: String, password: String, rePassword: String): Observable<Map<String, Any>>

    fun requestCollectArticle(id: Int): Observable<Map<String, Any>>

    fun requestUnCollectArticle(id: Int): Observable<Map<String, Any>>


}