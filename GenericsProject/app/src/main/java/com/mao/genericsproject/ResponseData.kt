package com.mao.genericsproject

/**
 *  author : maoqitian
 *  date : 2021/4/11 17:01
 *  description : 泛型类
 */
data class ResponseData<E> (
    var dataList : List<E>,
    var item : E
)