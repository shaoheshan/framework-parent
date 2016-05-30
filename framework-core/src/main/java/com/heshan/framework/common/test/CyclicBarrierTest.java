package com.heshan.framework.common.test;

import com.heshan.framework.common.redis.BidRedis;
import com.heshan.framework.common.redis.BidRedisFactory;
import com.heshan.framework.utils.date.DateUtils;

import java.util.Date;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author <a href="mailto:heshan664754022@gmail.com">Frank</a>
 * @version V1.0
 * @description
 * @date 2016/5/30 11:48
 */
public class CyclicBarrierTest {
    //初始化
    BidRedis jedis= BidRedisFactory.getBidRedis();


    public static void main(String[] args) {
        int count = 1000;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(count);
        ExecutorService executorService = Executors.newFixedThreadPool(count);
        for (int i = 0; i < count; i++)
            executorService.execute(new CyclicBarrierTest().new Task(cyclicBarrier));
        executorService.shutdown();
        while (!executorService.isTerminated()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    public class Task implements Runnable {
        private CyclicBarrier cyclicBarrier;
        public Task(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }
        @Override
        public void run() {
            try {
                // 等待所有任务准备就绪
                cyclicBarrier.await();
                StringBuilder str=new StringBuilder();
                str.append("PO-");
                String date=DateUtils.format(new Date());
                str.append(date);
                Long seq=jedis.incr(str.toString());
                System.out.println(str.append("-").append(seq));


                // 测试内容
                // 待测试的url
              /*  String host = "http://172.25.2.14/seqno?";
                String para = "sysTemNo=ERP&seqName=WH-ZONE-ID&iVar=00";
                System.out.println(host + para);
                URL url = new URL(host);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                // connection.setRequestMethod("POST");
                // connection.setRequestProperty("Proxy-Connection", "Keep-Alive");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                PrintWriter out = new PrintWriter(connection.getOutputStream());
                out.print(para);
                out.flush();
                out.close();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                String result = "";
                while ((line = in.readLine()) != null) {
                    result += line;
                }
                System.out.println(result);*/

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
