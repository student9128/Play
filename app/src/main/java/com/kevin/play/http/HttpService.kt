package com.kevin.play.http

import com.kevin.play.bean.*
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*
import java.util.*

/**
 * Created by Kevin on 2018/12/5<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
interface HttpService {
    @GET("https://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1&mkt=zh-CN")
    fun getBingWallpaper(): Observable<Splash>

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
    @FormUrlEncoded
    fun login(@Field("username") username: String, @Field("password") pwd: String): Observable<Map<String, Any>>

    @POST("user/register")
    @FormUrlEncoded
    fun register(@Field("username") username: String, @Field("password") pwd: String,
                 @Field("repassword") repwd: String): Observable<Map<String, Any>>

    @GET("user/logout/json")
    fun logout(): Observable<Map<String, Any>>


}