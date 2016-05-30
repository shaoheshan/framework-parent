package com.heshan.framework.common.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author <a href="mailto:heshan664754022@gmail.com">frank</a>
 * @version V1.0
 * @date 2016/4/11
 */
public class ExecutorTest {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"_111");
            }
        });
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"_222");
            }
        });
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"_333");
            }
        });
        int cpuNums = Runtime.getRuntime().availableProcessors();
        System.out.println(cpuNums);
        executorService.shutdown();
    }
}
