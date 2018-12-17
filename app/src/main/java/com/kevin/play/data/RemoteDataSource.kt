package com.kevin.play.data

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
    fun requestDataBanner():Observable<Map<String,Any>>
}