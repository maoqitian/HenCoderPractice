package com.mao.genericsproject

/**
 *  author : maoqitian
 *  date : 2021/4/11 16:59
 *  description : kotlin 泛型接口
 */
interface KotlinGenerics <T> {

    fun get() :T

    fun set(item :T)
}