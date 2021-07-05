/**
 * @Description: 线程间通信 wait() notifyAll()
 * @Author: maoqitian
 * @CreateDate: 2021/7/5 23:21
 */
public class WaitInteractionDemo implements InterfaceRun{

    private String nameStr;

    private synchronized void setName() {
        nameStr = "maoqitian";
        //已经赋值 通知所有等待队列中的线程 进入锁竞争队列
        notifyAll();
    }

    private synchronized void printName() {
        //加锁 wait()等待
        while (nameStr == null){
            try {
                //进入等待队列 释放锁
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("赋值name："+nameStr);
    }

    @Override
    public void runTest() {
        //创建两个线程 分别给共享变量赋值和打印共享变量的值

        //子线程1
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            printName();
        });
        thread.start();
        //子线程2
        Thread thread1 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setName();
        });
        thread1.start();

        try {
            //标识线程1 在此时让主线程等待其执行完在执行后面的代码
            //让线程之间执行呈线性关系
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("主线程继续执行");
    }

}
