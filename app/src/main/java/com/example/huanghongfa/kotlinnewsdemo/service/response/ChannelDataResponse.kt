package com.example.huanghongfa.kotlinnewsdemo.service.response

import com.example.huanghongfa.kotlinnewsdemo.entity.ChannelEntity

/**
 * Created by huanghongfa on 2017/6/21.
 */
data class ChannelDataResponse(
        var code: Int,
        var msg: String,
        val data: ChannelList
)
data class ChannelList (
        val channel_list: ArrayList<ChannelEntity>
)