package com.heshan.framework.redis;

import com.heshan.framework.utils.date.DateUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:heshan664754022@gmail.com">Frank</a>
 * @version V1.0
 * @description
 * @date 2016/5/30 11:48
 */
public class CyclicBarrierRedisLockTest {
    //初始化
   // BaseRedis jedis= RedisFactory.getBaseRedis();
    Jedis conn = new Jedis("localhost",6379,10000);

    public static void main(String[] args) {
        int count =100;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(count);
        ExecutorService executorService = Executors.newFixedThreadPool(count);
        for (int i = 0; i < count; i++)
            executorService.execute(new CyclicBarrierRedisLockTest().new Task(cyclicBarrier));
        executorService.shutdown();
        while (!executorService.isTerminated()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    public String acquireLockWithTimeout(
            Jedis conn, String lockName, long acquireTimeout, long lockTimeout)
    {
        String identifier = UUID.randomUUID().toString();
        String lockKey = "lock:" + lockName;
        int lockExpire = (int)(lockTimeout / 1000);

        long end = System.currentTimeMillis() + acquireTimeout;
        while (System.currentTimeMillis() < end) {//多少秒之内无法获取锁超时
            if (conn.setnx(lockKey, identifier) == 1){
                conn.expire(lockKey, lockExpire);
                return identifier;
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (conn.ttl(lockKey) == -1) {
                conn.expire(lockKey, lockExpire);
            }

            try {
                Thread.sleep(1);
            }catch(InterruptedException ie){
                Thread.currentThread().interrupt();
            }
        }

        // null indicates that the lock was not acquired
        return null;
    }

    public boolean releaseLock(Jedis conn, String lockName, String identifier) {
        String lockKey = "lock:" + lockName;

        while (true){
            conn.watch(lockKey);
            if (identifier.equals(conn.get(lockKey))){
                Transaction trans = conn.multi();
                trans.del(lockKey);
                List<Object> results = trans.exec();
                if (results == null){
                    continue;
                }
                return true;
            }

            conn.unwatch();
            break;
        }

        return false;
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
                System.out.println(Thread.currentThread().getId()+":"+System.currentTimeMillis()+"准备就绪");
                cyclicBarrier.await();
                System.out.println(Thread.currentThread().getId()+":"+System.currentTimeMillis()+":"+"开始执行");
                Long start=System.currentTimeMillis();
                while (true){
                    String key=acquireLockWithTimeout(conn,"test1",1000,1000);
                    if(key!=null){
                        System.out.println(Thread.currentThread().getId()+":"+System.currentTimeMillis()/1000+":"+"获得锁");
                        TimeUnit.SECONDS.sleep(3);
                        releaseLock(conn,"test1",key);
                        System.out.println(Thread.currentThread().getId()+":"+System.currentTimeMillis()/1000+":"+"释放锁");

                        break;
                    }
                    if((System.currentTimeMillis()-start)/1000>100){
                        //10s内无法获取锁任务退出
                        System.out.println(Thread.currentThread().getId()+":"+System.currentTimeMillis()/1000+":"+"100s内无法获取锁");
                        break;
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
