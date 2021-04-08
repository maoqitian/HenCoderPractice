package com.mao.library_binging

import android.app.Activity

/**
 * @Description: View findViewById 的自动绑定 对于 Activity
 * @author maoqitian
 * @date 2021/4/8 0008 17:50
 */
class ViewBing {

    companion object{

        //支持Activity
        fun bind(activity: Activity){
            //反射获取自动生成帮助绑定类的对象
            val bindingClass = Class.forName(activity.javaClass.canonicalName + "Binding")
            //构造方法调用
            val bindingConstructor = bindingClass.getDeclaredConstructor(activity.javaClass)
            bindingConstructor.newInstance(activity)
        }
    }
}