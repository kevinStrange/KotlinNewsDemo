package com.example.huanghongfa.kotlinnewsdemo.service.response

import com.example.huanghongfa.kotlinnewsdemo.entity.NewsEntity

/**
 * Created by huanghongfa on 2017/6/21.
 */
data class NewsDataResponse (
    var code: Int,
    var msg: String,
    val data: ArrayList<NewsEntity>
)