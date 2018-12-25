package com.kevin.play.http

import com.kevin.play.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
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
        return builder
    }

}