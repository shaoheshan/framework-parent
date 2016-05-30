package com.heshan.framework.common.redis;

import com.heshan.framework.common.redis.config.RedisShardInfo;
import com.heshan.framework.common.redis.config.ShardedRedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 *
 * @version : Ver 1.0
 * @date : 2015-7-29 下午6:14:53
 */
public class BidRedisFactory {
	private static BidRedis bidRedis = null;
	private static int index = 1;

	private BidRedisFactory() {

	}

	/**
	 * 初始化.
	 * @return
	 */
	private static BidRedis initBidRedis() {
		//logger.info("初始化Redis连接:" + BidRedisConstant.getRedisConf());
		RedisShardInfo redisShardInfo = new RedisShardInfo(
				BidRedisConstant.getRedisConf());
		// 配置连接池
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(1000); //最大连接数
		config.setMaxIdle(2000);// 对象最大空闲时间
		config.setMaxWaitMillis(3000L);// 获取对象时最大等待时间
		config.setTestOnBorrow(false);
		config.setTestWhileIdle(true);
		ShardedRedisPool shardedJedisPool = new ShardedRedisPool(config,
				redisShardInfo);
		BidRedisImpl bidRedisImpl = new BidRedisImpl();
		bidRedisImpl.setShardedJedisPool(shardedJedisPool);
		return bidRedisImpl;
	}

	public static BidRedis getBidRedis() {
		if (bidRedis == null) {
			synchronized (BidRedisFactory.class) {
				if (bidRedis == null) {
					bidRedis = initBidRedis();
				}
			}
		}
		return bidRedis;
	}

	public static void main(String[] args) {

		BidRedis bidRedis=BidRedisFactory.getBidRedis();
		//System.out.println(bidRedis.getString("c3af9374694b6a15f7766234e6d3f126_JZ_REG_USER_cgs1021llt"));
		for(int i=0; i<100; i++) {
			String key=generateKey();
			System.out.println(bidRedis.setString(key, "test111" + i));
		}
		//System.out.println(bidRedis.setString("test111_1", "test1"));
		//System.out.println(bidRedis.getString("test_1"));
      /* for(int i=0; i<100; i++) {
			String key=generateKey();
			System.out.println(bidRedis.getString(key));
		}*/
	}
	public static String generateKey(){
		return String.valueOf("test11C")+"_"+(index++);
	}


}
