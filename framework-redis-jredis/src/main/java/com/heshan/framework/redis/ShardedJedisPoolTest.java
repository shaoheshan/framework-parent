package com.heshan.framework.redis;


import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:heshan664754022@gmail.com">Frank</a>
 * @version V1.0
 * @date 2016/3/14
 */
public class ShardedJedisPoolTest {
    static ShardedJedisPool pool;
    static{
        JedisPoolConfig config =new JedisPoolConfig();//Jedis池配置
        config.setMaxTotal(500); //最大连接数
        config.setMaxIdle(500);// 对象最大空闲时间
        config.setMaxWaitMillis(3000L);// 获取对象时最大等待时间
        config.setTestOnBorrow(false);
        config.setTestWhileIdle(true);
        String hostA = "101.200.190.144";
        int portA = 6379;
        List<JedisShardInfo> jdsInfoList =new ArrayList<JedisShardInfo>(1);
        JedisShardInfo infoA = new JedisShardInfo(hostA, portA);
        //infoA.setPassword("test123");
        jdsInfoList.add(infoA);
        pool =new ShardedJedisPool(config, jdsInfoList);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        /*for(int i=0; i<10; i++){
            String key =generateKey();
            //key += "{aaa}";
            ShardedJedis jds =null;
            try {
                jds =pool.getResource();
                //System.out.println(key+":"+jds.getShard(key).getClient().getHost());
                System.out.println(jds.set(key,"1111111111111111111111111111111"));
                System.out.println(jds.get(key));
            }catch (Exception e) {
                e.printStackTrace();
            }
            finally{
                pool.returnResource(jds);
            }
        }*/
       /* try {
            BaseRedis jedis= BaseRedisFactory.getBidRedis();
            List<Person> list= Lists.newArrayList();
            Person person=new Person();
            person.setName("zhangsan");
            person.setAge(1);
            list.add(person);
           Map<String, List<Person>> user = new HashMap<String, List<Person>>();
            user.put("user",list);
            //map存入 redis
            jedis.setObject("user",user);
            Map<String, List<Person>> map1 =(Map<String, List<Person>>) jedis.getObject("user");
            System.out.println(map1.get("user"));
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", "fujianchao");
            map.put("password", "123");
            map.put("age", "12");
            // 存入一个map
            jedis.hmset("user1", map);
            // map key的个数
            System.out.println("map的key的个数" + jedis.hlen("user1"));
            // map key
            System.out.println("map的key" + jedis.hkeys("user1"));
            // map value
            System.out.println("map的value" + jedis.hvals("user1"));
        }catch (Exception e){

        }finally {
           // pool.returnResource(jedis);
        }*/
    }

    private static int index = 1;
    public static String generateKey(){
        return String.valueOf(Thread.currentThread().getId())+"_"+(index++);
    }
}