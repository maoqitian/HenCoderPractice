package com.mao.androidthread;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author maoqitian
 * @Description: 仿照 Handler
 * @date 2021/7/6 0006 17:13
 */
class CustomThread extends Thread {

    Looper looper = new Looper();

    @Override
    public void run() {
        looper.loop();
    }


    class Looper{
        private Runnable task;
        //退出循环标识
        private AtomicBoolean quit = new AtomicBoolean(false);

        synchronized void setTask(Runnable task){
            this.task = task;
        }

        void quit(){
            quit.set(true);
        }

       void loop() {
            while(!quit.get()){
                synchronized (this){
                    if (task!=null){
                        task.run();
                        task = null;
                    }
                }
            }
        }
    }
}
