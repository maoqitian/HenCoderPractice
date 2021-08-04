package com.mao.library_binging

import android.app.Activity

/**
 * @Description: View findViewById 的自动绑定 对于 Activity，
 * 反射获取 AbstractProcessor 自动生成的 XXXXActivityBinding 类，调用它的构造方法进行自动绑定
 * @author maoqitian
 * @date 2021/4/8 0008 17:50
 */
class ViewBing {

    companion object{

        //支持Activity
        fun bind(activity: Activity){
            //反射获取自动生成帮助绑定类的对象
            //activity.javaClass.canonicalName 实际的类名称 打印为 com.mao.aptproject#MainActivity
            //activity.javaClass.name 打印为 com.mao.aptproject.MainActivity
            val bindingClass = Class.forName(activity.javaClass.canonicalName + "Binding")
            //构造方法调用
            val bindingConstructor = bindingClass.getDeclaredConstructor(activity.javaClass)
            bindingConstructor.newInstance(activity)
        }
    }
}