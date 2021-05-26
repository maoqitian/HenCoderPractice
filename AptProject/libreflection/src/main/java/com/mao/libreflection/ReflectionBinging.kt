package com.mao.libreflection

import android.app.Activity

/**
 * @Description: 手动遍历 Activity 的成员变量 获取注解值来进行绑定（手动调用）
 * @author maoqitian
 * @date 2021/4/8 0008 13:28
 */
class ReflectionBinging {

    companion object{
        fun bind(activity: Activity){
            activity::class.java.declaredFields.forEach { field ->

                //遍历每个成员对象 拿到注解对应的 value 进行数据绑定
                val bindViewAnnotation = field.getAnnotation(BindView::class.java)
                if (bindViewAnnotation!=null){
                    //解除访问限制
                    field.isAccessible = true
                    //自动绑定view
                    field.set(activity,activity.findViewById(bindViewAnnotation.value))
                }

            }
        }
    }
}