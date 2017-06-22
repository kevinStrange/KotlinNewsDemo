package com.example.huanghongfa.kotlinnewsdemo.service.response

import com.example.huanghongfa.kotlinnewsdemo.entity.User

/**
 * Created by huanghongfa on 2017/6/21.
 */
data class UserResponse(

        var code: Int,
        var msg: String,
        var data: User
)