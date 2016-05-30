package com.heshan.framework.common.concurrent;

/**
 * @author <a href="mailto:heshanshao@ebnew.com">heshanshao</a>
 * @version V1.0
 * @description
 * @date 2016/5/7 11:23
 */
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;

public class ConcurrentLinkQueueList {
    static ConcurrentLinkedQueue linkedQueue=new ConcurrentLinkedQueue();

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(100);
        Semaphore semaphore=new Semaphore(0);

        for (int i = 1; i <= 100; i++) {
            WorkerThread workerThread=new WorkerThread(i);
            executor.execute(workerThread);

        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 1; i <= 100; i++) {
           executor.execute(new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(linkedQueue.poll());
                }
            }));

        }
        executor.shutdown(); // This will make the executor accept no new threads and finish all existing threads in the queue
        while (!executor.isTerminated()){

        }
        System.out.println("111==="+linkedQueue.size());

    }
    public static class WorkerThread implements Runnable {

         private int command;
        public WorkerThread(int s){
            this.command=s;
        }

        @Override
        public void run() {
            System.out.println("add--"+command);
            linkedQueue.add(this.command);
        }

    }

}