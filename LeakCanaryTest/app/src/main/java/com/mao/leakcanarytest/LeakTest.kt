package com.mao.leakcanarytest

import android.os.SystemClock
import android.util.Log

/**
 * @Description:
 * @author maoqitian
 * @date 2021/6/3 0003 16:10
 */
class LeakTest(var obj : Any?) : Thread() {

    override fun run() {
        super.run()
        var localVar = obj //传入的值赋值给本地局部变量
        obj = null  //确保成员变量不再引用


        SystemClock.sleep(10000)
        Log.e("maoqitian","localVar: ${localVar.toString()}")

    }


    fun leak(){
        start()
    }

}