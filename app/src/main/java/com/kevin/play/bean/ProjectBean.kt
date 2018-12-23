package com.kevin.play.bean

import com.google.gson.annotations.SerializedName

/**
 * Created by Kevin on 2018/12/20<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
 data class ProjectBean(
    @SerializedName("data")
    val data: List<ProjectTitle>,
    val errorCode: Int,
    val errorMsg: String
)

data class ProjectTitle(
    val children: List<Any>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)