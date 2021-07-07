package com.mao.androidthread

/**
 * @Description:
 * @author maoqitian
 * @date 2021/7/6 0006 17:23
 */
fun main() {
    val customThread = CustomThread()
    //开启子线程
    customThread.start()
    //主线程休眠三秒
    Thread.sleep(3000)
    //给子线程设置任务
    /*customThread.setTask {
        println("线程被设置执行了")
    }

    customThread.quit()*/


}