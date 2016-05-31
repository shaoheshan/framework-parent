package com.heshan.framework.redis;

import com.heshan.framework.redis.config.RedisShardInfo;
import com.heshan.framework.redis.config.ShardedRedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 *
 * @version : Ver 1.0
 * @date : 2015-7-29 下午6:14:53
 */
public class RedisFactory {
	private static BaseRedis baseRedis = null;
	private static int index = 1;

	private RedisFactory() {

	}

	/**
	 * 初始化.
	 * @return
	 */
	private static BaseRedis initBaseRedis() {
		//logger.info("初始化Redis连接:" + RedisConstant.getRedisConf());
		RedisShardInfo redisShardInfo = new RedisShardInfo(
				RedisConstant.getRedisConf());
		// 配置连接池
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(1000); //最大连接数
		config.setMaxIdle(2000);// 对象最大空闲时间
		config.setMaxWaitMillis(3000L);// 获取对象时最大等待时间
		config.setTestOnBorrow(false);
		config.setTestWhileIdle(true);
		ShardedRedisPool shardedJedisPool = new ShardedRedisPool(config,
				redisShardInfo);
		BaseRedisImpl BaseRedisImpl = new BaseRedisImpl();
		BaseRedisImpl.setShardedJedisPool(shardedJedisPool);
		return BaseRedisImpl;
	}

	public static BaseRedis getBaseRedis() {
		if (baseRedis == null) {
			synchronized (RedisFactory.class) {
				if (baseRedis == null) {
					baseRedis = initBaseRedis();
				}
			}
		}
		return baseRedis;
	}

	public static void main(String[] args) {

		BaseRedis baseRedis= RedisFactory.getBaseRedis();
		//System.out.println(BaseRedis.getString("c3af9374694b6a15f7766234e6d3f126_JZ_REG_USER_cgs1021llt"));
		for(int i=0; i<100; i++) {
			String key=generateKey();
			System.out.println(baseRedis.setString(key, "test111" + i));
		}
		//System.out.println(BaseRedis.setString("test111_1", "test1"));
		//System.out.println(BaseRedis.getString("test_1"));
      /* for(int i=0; i<100; i++) {
			String key=generateKey();
			System.out.println(BaseRedis.getString(key));
		}*/
	}
	public static String generateKey(){
		return String.valueOf("test11C")+"_"+(index++);
	}


}
