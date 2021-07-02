package com.example.app

import com.example.app.entity.User

/**
 * @Description:
 * @author maoqitian
 * @date 2021/7/2 0002 16:00
 */

fun main(){
    val user = User("","","")
    val copy = user.copy()
    println(user == copy)
    println(user === copy)
}