package com.heshan.framework.common.test.thread;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author <a href="mailto:heshan664754022@gmail.com">Frank</a>O
 * @version V1.0
 * @description
 * @date 2017/8/13 14:15
 */
public class Queue {

    Object obj=new Object();
    public  static  int size=10;
    ConcurrentLinkedQueue queue=new ConcurrentLinkedQueue();
    public void add(int i){
          if (queue.size()<size){
              System.out.println("put the value:"+i);
              queue.offer(i);
              synchronized (obj){
                  obj.notifyAll();
              }
          }else{
              synchronized (obj){
                  try {
                      System.out.println("已满，等待取值");
                      obj.wait();
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
              }
              System.out.println("put the value:"+i);
              queue.offer(i);
          }

    }
    public void get(){
        if (queue.size()>0){
            System.out.println(Thread.currentThread().getId()+":"+"get the value:"+ queue.poll());
            synchronized (obj){
                obj.notifyAll();
            }
        }else{
            synchronized (obj){
                try {
                    System.out.println("队列已空，等待放值");
                    obj.wait();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (queue.size()>0) {
                System.out.println(Thread.currentThread().getId() + ":" + "get the value:" + queue.poll());
            }
        }
    }

}
