package com.heshan.framework.common.concurrent;

/**
 * @author <a href="mailto:heshan664754022@gmail.com">frank</a>
 * @version V1.0
 * @date 2016/4/13
 */
public class volatileTest {

    int a=0;
    volatile  boolean flag=false;

    public  void  writer(){
        a=1;
        flag=true;
    }
    public  void  reader(){
        while (flag) {
            int i = a;
            System.out.println(i);
        }
    }

    public static void main(String[] args) {

       final volatileTest vt=new volatileTest();
        Thread t1=new Thread(new Runnable() {
            @Override
            public void run() {
                vt.writer();
            }
        });
        Thread t2=new Thread(new Runnable() {
            @Override
            public void run() {
                vt.reader();
            }
        });
        t1.start();
        t2.start();
    }


}
