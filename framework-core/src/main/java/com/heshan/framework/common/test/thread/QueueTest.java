package com.heshan.framework.common.test.thread;

/**
 * @author <a href="mailto:heshan664754022@gmail.com">Frank</a>
 * @version V1.0
 * @description
 * @date 2017/8/13 14:56
 */
public class QueueTest {
    static  class  T1 extends Thread{
        Queue queue;
        public T1(Queue queue){
            this.queue=queue;
        }

        @Override
        public void run() {
            for (int i=0;i<10000;i++){
                queue.add(i);
            }

         }
    }
    static  class  T2 extends Thread{
        Queue queue;
        public T2(Queue queue){
            this.queue=queue;
        }

        @Override
        public void run() {
            for (int i=0;i<10000;i++){
                queue.get();
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {
        Queue queue=new Queue();
        T1 t1=new T1(queue);
        T2 t2=new T2(queue);
        T2 t3=new T2(queue);
        t1.start();
        //Thread.sleep(1000);
        t2.start();
        t3.start();
    }

}
