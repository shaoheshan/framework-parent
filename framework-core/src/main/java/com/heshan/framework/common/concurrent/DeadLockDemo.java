package com.heshan.framework.common.concurrent;

/**
 * @author <a href="mailto:heshan664754022@gmail.com">frank</a>
 * @version V1.0
 * @date 2016/4/6
 * @description   死锁场景是两个锁互相等待对方释放资源
 */
public class DeadLockDemo {
    private  static  String A="A";
    private  static  String B="B";
    private    A1 a1=new A1();
    private    B1 b1=new B1();
    public static void main(String[] args) {
        new DeadLockDemo().deadLock();
    }
    private  void deadLock(){

        Thread t1=new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (a1){
                    try {
                        System.out.println("t1进入A..");
                        Thread.currentThread().sleep(2000);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    System.out.println("t1要进入B..");
                    synchronized (b1){
                        System.out.println("1");
                    }
                }
            }
        });

        Thread t2=new Thread(new Runnable() {
            @Override
            public void run() {

                synchronized (b1){
                    System.out.println("t2进入b1..");
                    synchronized (a1){
                        System.out.println("2");
                    }
                }
            }
        });
        t1.start();
        t2.start();
    }

}
class  A1{

}
class  B1{

}
