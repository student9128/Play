package com.kevin.play.http

import com.kevin.play.bean.NavBean
import com.kevin.play.bean.NavData
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
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

}