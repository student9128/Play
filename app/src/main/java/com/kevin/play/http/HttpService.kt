package com.kevin.play.http

import com.kevin.play.bean.NavBean
import com.kevin.play.bean.NavData
import com.kevin.play.bean.ProjectBean
import com.kevin.play.bean.ProjectListBean
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

/**
 * Created by Kevin on 2018/12/5<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
interface HttpService {
    @GET("wxarticle/chapters/json")
    fun getWxArticleList(): Call<Map<String, Any>>

    @GET("wxarticle/list/{id}/{page}/json")
    fun getWxArticleHistory(@Path("id") id: String, @Path("page") page: String): Observable<Map<String, Any>>

    @GET("article/list/{page}/json")
    fun getArticleList(@Path("page") page: Int): Observable<Map<String, Any>>

    @GET("banner/json")
    fun getBanner(): Observable<Map<String, Any>>

    @GET("navi/json")
    fun getNav(): Observable<NavBean>

    @GET("project/tree/json")
    fun getProjectTree(): Observable<ProjectBean>

    @GET("project/list/{page}/json?")
    fun getProjectList(@Path("page") page: Int, @Query("cid") cid: String): Observable<ProjectListBean>

    @POST("user/login")
    fun login(@Query("username") username: String, @Query("password") pwd: String)

    @POST("user/register")
    fun register(@Query("username") username: String, @Query("password") pwd: String,
                 @Query("repassword") repwd: String)

    @GET("user/logout/json")
    fun logout()

}