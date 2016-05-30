package com.heshan.framework.common.concurrent;

/**
 * @author <a href="mailto:heshanshao@ebnew.com">heshanshao</a>
 * @version V1.0
 * @description synchronized测试</br>
 * @date 2016/4/19 10:32
 */
public class SynchronizedTest {
        public  synchronized void method1(){
            try {
                System.out.println("Method 1 start");
                System.out.println("Method 1 execute");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Method 1 end");
        }

        public  synchronized void method2(){
            System.out.println("Method 2 start");
            try {
                System.out.println("Method 2 execute");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Method 2 end");
        }

        public static void main(String[] args) {
            final SynchronizedTest test = new SynchronizedTest();
            Thread t1=new Thread(new Runnable() {
                @Override
                public void run() {
                    test.method1();
                }
            });

            /*new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        t1.join();
                        test.method2();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();*/
            Thread t3=new Thread(new Demino(t1));
            t1.start();
            try {
                t3.start();
                t3.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("all end");
        }

    static  class  Demino implements  Runnable{
        private  Thread thread;
        public  Demino(Thread thread){
            this.thread=thread;
        }

        @Override
        public void run() {
            System.out.println("begin");

            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("end1");
        }
    }
    }

