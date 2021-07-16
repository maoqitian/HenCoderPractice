package com.example.app

import com.example.app.entity.User
import com.example.core.retrofit.Repo
import com.example.core.retrofit.RetrofitApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

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

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
    val api = retrofit.create(RetrofitApi::class.java)

    val listRepos = api.listRepos("maoqitian")

    listRepos.enqueue(object :Callback<List<Repo>>{
        override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {

        }

        override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
        }

    })
    listRepos.execute()
}