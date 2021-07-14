package com.example.core.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


/**
 * @Description:
 * @author maoqitian
 * @date 2021/7/14 0014 17:35
 */
interface RetrofitApi {
    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String): Call<List<Repo>>
}