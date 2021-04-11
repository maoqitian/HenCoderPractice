package com.mao.genericsproject.fruit;

import com.mao.genericsproject.fruit.food;

import java.io.Serializable;

/**
 * @author maoqitian
 * @Description:
 * @date 2021/4/9 0009 13:53
 */
interface Eater <T extends food> {

    void eat(T food);


    /**
     * 泛型方法 作业 1
     * @param param 1. 是 Runnable；2. 是 Serializable。
     * @param <E> 参数类型
     * @return 返回参数本身类型
     */
    <E extends Runnable & Serializable> E someMethod(E param);
}
