package com.heshan.framework.common.test.thread;

/**
 * @author <a href="mailto:heshan664754022@gmail.com">Frank</a>
 * @version V1.0
 * @description
 * @date 2017/8/13 13:58
 */
public class joinTest {

    static  class T1 extends  Thread{


        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("T1 结束执行");
        }
    }
    static class T2 extends  Thread{
        Thread thread;
        public T2(Thread thread){
            this.thread=thread;
        }

        @Override
        public void run() {
            try {
                thread.join();
                System.out.println("T2 结束执行");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
    static class T3 extends  Thread{
        Thread thread;
        public T3(Thread thread){
            this.thread=thread;
        }

        @Override
        public void run() {
            try {
                thread.join();
                System.out.println("T3 结束执行");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args)  {
            T1 t1=new T1();
            T2 t2=new T2(t1);
            T3 t3=new T3(t2);
            t3.start();//线程尚未启动 调用join无效
            //Thread.sleep(1000);
            t2.start();
            //Thread.sleep(1000);
            t1.start();
    }
}
