package com.mao.genericsproject.fruit;

import com.mao.genericsproject.fruit.food;

/**
 * @author maoqitian
 * @Description:
 * @date 2021/4/9 0009 13:53
 */
interface Eater <T extends food> {

    void eat(T food);
}
