package com.heshan.framework.common.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author <a href="mailto:heshan664754022@gmail.com">frank</a>
 * @version V1.0
 * @date 2016/4/11
 */
public class futureTest {
    public static void main(String[] args) throws InterruptedException, ExecutionException
    {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Future<String>> resultList = new ArrayList<Future<String>>();
        // 创建10个任务并执行
        for (int i = 0; i < 10; i++)
        {
            // 使用ExecutorService执行Callable类型的任务，并将结果保存在future变量中
            Future<String> future = executorService.submit(new TaskWithResult(i));
            resultList.add(future);
        }
        for (Future<String> future : resultList)
        {
            while (!future.isDone());// Future返回如果没有完成，则一直循环等待，直到Future返回完成 会阻碍下一个线程的执行
            {
                System.out.println(future.get()); // 打印各个线程（任务）执行的结果
            }
        }
        executorService.shutdown();
    }
}
    class TaskWithResult implements Callable<String>
    {
        private int id;
        public TaskWithResult(int id)
        {
            this.id = id;
        }
        public String call() throws Exception
        {
            return "执行结果"+id;
        }
}
