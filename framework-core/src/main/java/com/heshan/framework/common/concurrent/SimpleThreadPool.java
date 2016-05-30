package com.heshan.framework.common.concurrent;

/**
 * @author <a href="mailto:heshanshao@ebnew.com">heshanshao</a>
 * @version V1.0
 * @description
 * @date 2016/5/7 11:23
 */
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class SimpleThreadPool {

    private  static  Long num=100000000l;
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        Share share=new Share();
        Long begin=System.currentTimeMillis();
        for (int i = 1; i <= 10; i++) {
            WorkerThread workerThread=new WorkerThread(i,share);
            executor.execute(workerThread);

        }
        executor.shutdown(); // This will make the executor accept no new threads and finish all existing threads in the queue
        while (!executor.isTerminated()) { // Wait until all threads are finish,and also you can use "executor.awaitTermination();" to wait
        }
        Long end=System.currentTimeMillis();

        System.out.println("Finished all threads="+share.get().longValue());
        System.out.println(String.format("time is %s",end-begin));
        long value=0l;begin=System.currentTimeMillis();
        for (int i = 1; i <= 10*num; i++) {
                 value+=i;
        }
        end=System.currentTimeMillis();
        System.out.println("累加计算="+share.get().longValue());
        System.out.println(String.format("time is %s",end-begin));


    }
    public static class WorkerThread implements Runnable {

         private int command;
         Share share=null;
        public WorkerThread(int s,Share share){
            this.command=s;
            this.share=share;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+" Start. Command = "+command);
            processCommand();

            System.out.println(Thread.currentThread().getName()+" End.");
        }

        private void processCommand() {
            long v=0l;
            for (long j=(command-1)*num+1l;j<=command*num;j++){
                v+=j;
            }
            share.add(v);
        }


    }
    public static class Share {

        AtomicLong atomicLong=new AtomicLong(0l);

        public void add(long i){
            atomicLong.addAndGet(i);
        }

        public Long get(){
            return  atomicLong.get();
        }

    }

}