package com.kevin.play.bean

/**
 * Created by Kevin on 2018/12/25<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
data class Splash(
    val images: List<Image>,
    val tooltips: Tooltips
)

data class Image(
    val bot: Int,
    val copyright: String,
    val copyrightlink: String,
    val drk: Int,
    val enddate: String,
    val fullstartdate: String,
    val hs: List<Any>,
    val hsh: String,
    val quiz: String,
    val startdate: String,
    val title: String,
    val top: Int,
    val url: String,
    val urlbase: String,
    val wp: Boolean
)

data class Tooltips(
    val loading: String,
    val next: String,
    val previous: String,
    val walle: String,
    val walls: String
)