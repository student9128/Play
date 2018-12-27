package com.kevin.play.bean

import com.google.gson.annotations.SerializedName

/**
 * Created by Kevin on 2018/12/18<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
data class HomeArticleListBean(
    @SerializedName("data")
    val data: Data,
    val errorCode: Int,
    val errorMsg: String
)

data class Data(
    val curPage: Int,
    @SerializedName("datas")
    val content: List<Content>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)

data class Content(
    val apkLink: String,
    val author: String,
    val chapterId: Int,
    val chapterName: String,
    var collect: Boolean,
    val courseId: Int,
    val desc: String,
    val envelopePic: String,
    val fresh: Boolean,
    val id: Int,
    val link: String,
    val niceDate: String,
    val origin: String,
    val projectLink: String,
    val publishTime: Long,
    val superChapterId: Int,
    val superChapterName: String,
    @SerializedName("tags")
    val tags: List<ProjectTag>,
    val title: String,
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int
)

data class Tag(
    val name: String,
    val url: String
)