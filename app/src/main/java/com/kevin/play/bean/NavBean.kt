package com.kevin.play.bean

import com.google.gson.annotations.SerializedName

/**
 * Created by Kevin on 2018/12/20<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
data class NavBean(
    @SerializedName("data")
    val data: List<NavData>,
    val errorCode: Int,
    val errorMsg: String
)

data class NavData(
    val articles: List<Article>,
    val cid: Int,
    val name: String
)

data class Article(
    val apkLink: String,
    val author: String,
    val chapterId: Int,
    val chapterName: String,
    val collect: Boolean,
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
    val tags: List<Any>,
    val title: String,
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int
)