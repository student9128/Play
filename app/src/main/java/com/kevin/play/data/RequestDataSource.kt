package com.kevin.play.data

import com.kevin.play.bean.NavBean
import com.kevin.play.bean.NavData
import com.kevin.play.http.AppRetrofit
import io.reactivex.Observable

/**
 * Created by Kevin on 2018/12/6<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class RequestDataSource : RemoteDataSource {

    private val httpService = AppRetrofit.appRetrofit.getHttpService()

    companion object {
        private var requestDataSource: RequestDataSource? = null
            get() {
                if (field == null) {
                    field = RequestDataSource()
                }
                return field
            }

        @Synchronized
        fun getSingleInstance(): RequestDataSource? {
            return requestDataSource
        }
    }

    override fun requestDataBanner(): Observable<Map<String, Any>> {
        return httpService.getBanner()
    }

    override fun requestHomeArticleList(page: Int): Observable<Map<String, Any>> {
        return httpService.getArticleList(page)
    }

    override fun requestDataNav(): Observable<NavBean> {
        return httpService.getNav()
    }

}