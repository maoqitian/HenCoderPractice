/**
 * @Description:
 * @Author: maoqitian
 * @CreateDate: 2021/7/5 23:08
 */
public class Main {

    public static void main(String[] args) {
       //runThreadInteractionDemo();
        runWaitDemo();
    }

    static void runThreadInteractionDemo(){
        new ThreadInteractionDemo().runTest();
    }

    static void runWaitDemo(){
        new WaitInteractionDemo().runTest();
    }
}
