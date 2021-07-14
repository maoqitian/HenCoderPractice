package com.example.app

import com.example.app.entity.User
import com.example.core.retrofit.RetrofitApi
import retrofit2.Retrofit

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

    val retrofit = Retrofit.Builder().baseUrl("https://api.github.com/").build()
    val api = retrofit.create(RetrofitApi::class.java)

    val listRepos = api.listRepos("maoqitian")

    listRepos.enqueue()
    listRepos.execute()
}