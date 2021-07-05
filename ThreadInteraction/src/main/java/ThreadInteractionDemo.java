/**
 * @Description: 线程终止 目的为节省资源
 * @Author: maoqitian
 * @CreateDate: 2021/7/5 22:48
 */
public class ThreadInteractionDemo implements InterfaceRun{
    @Override
    public void runTest() {

        Thread thread = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++) {
                    if (isInterrupted()) {
                        // 擦屁股
                        return;
                    }
                   /* try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        // 擦屁股
                        return;
                    }*/
                    System.out.println("number: " + i);
                }
            }
        };

        thread.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //终止线程
        thread.interrupt();
    }
}
