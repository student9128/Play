package com.kevin.play.bean

import com.google.gson.annotations.SerializedName

/**
 * Created by Kevin on 2018/12/14<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
data class HomeBannerBean(@SerializedName("data") val data: List<HomeBannerData>, val errorCode: String, val errorMessage: String)

data class HomeBannerData(val desc: String, val id: Int, val imagePath: String, val isVisible: Int, val order: Int, val title: String,
                          val type: Int, val url: String)