package com.kevin.play.bean

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by Kevin on 2018/12/23<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
data class LoginBean(
    var errorCode: Int,
    var errorMsg: String?,
    @SerializedName("data")
    var data: LoginData
) : Serializable

data class LoginData(
    var id: Int,
    var username: String,
    var password: String,
    var icon: String?,
    var type: Int,
    var collectIds: List<Int>?
) : Serializable
