package com.mao.libreflection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author maoqitian
 * @Description: 模仿 黄油刀 自定绑定 View
 * @date 2021/4/8 0008 13:22
 */

//运行时也保存
@Retention(RetentionPolicy.RUNTIME)
//作用于方法
@Target(ElementType.FIELD)
public @interface BindView {

    //默认值 int 对应 view id
    int value();
}
