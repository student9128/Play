package com.kevin.play.http

import android.content.Context
import com.kevin.play.BuildConfig
import com.kevin.play.base.BaseApplication
import com.kevin.play.constant.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.HashSet
import java.util.concurrent.TimeUnit

/**
 * Created by Kevin on 2018/12/5<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 * Http request utils
 */
open class AppRetrofit {
    companion object {
        const val BASE_URL = "http://www.wanandroid.com/"
        const val CONNECT_TIME_OUT = 30L
        const val READ_TIME_OUT = 60L
        //单例模式
//        private var appRetrofit: AppRetrofit? = null
//            get() {
//                if (field == null) {
//                    field = AppRetrofit()
//                }
//                return field
//            }
//
//        @Synchronized
//        fun getSingleInstance(): AppRetrofit? {
//            return appRetrofit
//        }
        //双重校验 单例模式
        val appRetrofit: AppRetrofit by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            AppRetrofit()
        }


        //与java对应的单例模式

//        fun getInstance(): AppRetrofit? {
//            if (appRetrofit == null) {
//                synchronized(AppRetrofit.javaClass) {
//                    appRetrofit = AppRetrofit()
//                }
//            }
//            return appRetrofit
//        }


    }

    private var retrofit: Retrofit

    //    fun getHttpService(): HttpService? {
//        return getInstance()?.create(HttpService::class.java)
//    }
    /**
     * get HttpService
     */
    fun getHttpService(): HttpService {
        return appRetrofit.create(HttpService::class.java)
    }


    constructor() {
        retrofit = Retrofit.Builder()
            .client(initBuilder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    constructor(baseUrl: String) {
        retrofit = Retrofit.Builder()
            .client(initBuilder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }

    private fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }

    /**
     * init okhttp client for retrofit
     */
    private fun initBuilder(): OkHttpClient.Builder {
        var interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        var builder = OkHttpClient.Builder()
        builder.sslSocketFactory(
            SSLSocketFactoryUtils.createSSLSocketFactory(),
            SSLSocketFactoryUtils.createTrustAllManager()
        )
            .hostnameVerifier(SSLSocketFactoryUtils.TrustAllHostnameVerifier())
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(interceptor)
        }
        builder.retryOnConnectionFailure(true)
            .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(SaveCookieInterceptor())
            .addInterceptor(RequestCookiesInterceptor())
        return builder
    }


    class SaveCookieInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val response = chain.proceed(request)
            val requestUrl = request.url().toString()
            val domain = request.url().host()
            if ((requestUrl.contains(Constants.KEY_LOGIN) || requestUrl.contains(Constants.KEY_REGISTER))
                && response.header(Constants.KEY_SET_COOKIE)!!.isNotEmpty()
            ) {
                val cookies = response.headers(Constants.KEY_SET_COOKIE)
                val cookie = encodeCookie(cookies)
                saveCookie(requestUrl, domain, cookie)

            }

            return response

        }

        private fun saveCookie(url: String, domain: String, cookies: String) {
            val context = BaseApplication.getContext()
            val sp = context.getSharedPreferences(Constants.KEY_COOKIE_STORE, Context.MODE_PRIVATE)
            val editor = sp.edit()

            if (url.isNotEmpty()) {
                editor.putString(url, cookies)
            }
            if (domain.isNotEmpty()) {
                editor.putString(domain, cookies)
            }
            editor.commit()

        }

        //整合cookie为唯一字符串
        private fun encodeCookie(cookies: List<String>): String {
            var sb = StringBuilder()
            val set = HashSet<String>()
            cookies.map { cookie -> cookie.split(";").toTypedArray() }
                .forEach { c ->
                    c.filterNot { s -> set.contains(s) }
                        .forEach { set.add(it) }
                }
            val iterator = set.iterator()
            while (iterator.hasNext()) {
                val cookie = iterator.next()
                sb.append(cookie).append(";")
            }
            val lastIndexOf = sb.lastIndexOf(";")
            if (sb.length - 1 == lastIndexOf) {
                sb.deleteCharAt(lastIndexOf)
            }
            return sb.toString()
        }

    }

    class RequestCookiesInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val builder = request.newBuilder()
            val requestUrl = request.url().toString()
            val domain = request.url().host()
            val cookie = getCookie(domain)
            if (cookie.isNotEmpty()) {
                builder.addHeader("Cookie", cookie)
            }
            return chain.proceed(builder.build())
        }

        private fun getCookie(domain: String): String {
            val context = BaseApplication.getContext()
            val sp = context.getSharedPreferences(Constants.KEY_COOKIE_STORE, Context.MODE_PRIVATE)
//            if (url.isNotEmpty() && sp.contains(url)) {
//                return sp.getString(url, "")
//            }

            return if (domain.isNotEmpty()) {
                sp.getString(domain, "")
            } else {
                ""
            }

        }

    }


}