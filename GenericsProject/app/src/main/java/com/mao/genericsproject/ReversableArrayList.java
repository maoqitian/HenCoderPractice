package com.mao.genericsproject;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author maoqitian
 * @Description:
 * @date 2021/4/9 0009 13:55
 */
class ReversableArrayList<E> extends ArrayList<E> {

    public void reverse(){
        Collections.reverse(this);
    }
}
