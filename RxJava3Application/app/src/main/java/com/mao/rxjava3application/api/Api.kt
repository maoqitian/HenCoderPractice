package com.mao.rxjava3application.api

import com.mao.rxjava3application.model.ResponseData
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @Description:
 * @author maoqitian
 * @date 2021/3/29 0029 16:08
 */
public interface Api {

    @GET("users/{username}/repos")
    fun getRepo(@Path("username") userName:String) :Single<List<ResponseData>>
}