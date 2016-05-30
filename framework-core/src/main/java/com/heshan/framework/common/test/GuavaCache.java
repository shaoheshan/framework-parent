package com.heshan.framework.common.test;

import com.heshan.framework.common.string.EmptyUtils;
import com.google.common.cache.*;
import com.google.common.collect.Maps;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:heshan664754022@gmail.com">frank</a>
 * @version V1.0
 * @date 2016/3/24
 */
public class GuavaCache {
    private  static Boolean status=false;

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //缓存接口这里是LoadingCache，LoadingCache在缓存项不存在时可以自动加载缓存
        LoadingCache<Integer,Student> studentCache
                //CacheBuilder的构造函数是私有的，只能通过其静态方法newBuilder()来获得CacheBuilder的实例
                = CacheBuilder.newBuilder()
                //设置并发级别为8，并发级别是指可以同时写缓存的线程数
                .concurrencyLevel(8)
                        //设置写缓存后8秒钟过期
                .expireAfterWrite(8, TimeUnit.SECONDS)
                        //设置缓存容器的初始容量为10
                .initialCapacity(10)
                        //设置缓存最大容量为100，超过100之后就会按照LRU最近虽少使用算法来移除缓存项
                .maximumSize(100)
                        //设置要统计缓存的命中率
                .recordStats()
                        //设置缓存的移除通知
                .removalListener(new RemovalListener<Object, Object>() {
                    @Override
                    public void onRemoval(RemovalNotification<Object, Object> notification) {
                        System.out.println(notification.getKey() + " was removed, cause is " + notification.getCause());
                    }
                })
                        //build方法中可以指定CacheLoader，在缓存不存在时通过CacheLoader的实现自动加载缓存
                .build(
                        new CacheLoader<Integer, Student>() {
                            @Override
                            public Student load(Integer key) throws Exception {
                                System.out.println("load student " + key);
                                Student student = new Student();
                                student.setId(key);
                                student.setName("name " + key);
                                return student;
                            }
                        }
                );
        Cache<String,String> cache=CacheBuilder.newBuilder().expireAfterWrite(8, TimeUnit.SECONDS).recordStats().build();
        cache.put("test1", "test1");
        long start=System.currentTimeMillis();
        System.out.println(cache.getIfPresent("test1"));
        for (int i=0;i<5;i++) {

            Thread t=new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!status) {
                        try {
                            System.out.println("current thread " + Thread.currentThread().getId()+":"+System.currentTimeMillis());
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            t.setDaemon(true);
            t.setName("name"+i);
            t.start();

        }
        long end=0L;

        while(!status){
            if(EmptyUtils.isEmpty(cache.getIfPresent("test1"))) {
                end=System.currentTimeMillis();
                status = true;
            }
        }
       /* for (int i=0;i<20;i++) {
            //从缓存中得到数据，由于我们没有设置过缓存，所以需要通过CacheLoader加载缓存数据
            Student student = studentCache.get(1);
            System.out.println(student);
            //休眠1秒
            TimeUnit.SECONDS.sleep(1);
        }*/

        System.out.println("cache stats:");
        //最后打印缓存的命中率等 情况
        System.out.println(cache.stats().toString());
        System.out.println(end-start+":"+(end-start)/1000);
        Map<String,String> maps= Maps.newHashMap();
        maps.put("1","11");
        Set<Map.Entry<String,String>> str=maps.entrySet();
        for(Map.Entry<String,String> entry:str){
            System.out.println(entry.getKey());
        }
        Iterator<Map.Entry<String,String>> it=maps.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String,String> entry=it.next();
        }
        TimeUnit.SECONDS.sleep(1);
        TimeUnit.MILLISECONDS.sleep(1);
    }
}
