package com.heshan.framework.common.redis.config;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.exceptions.JedisException;

/**
 * @author <a href="mailto:heshan664754022@gmail.com">frank</a>
 * @version V1.0
 * @date 2016/3/11
 */
public class ShardedRedisPool extends ShardedJedisPool {

    public ShardedRedisPool(JedisPoolConfig poolConfig, RedisShardInfo shardInfo) {
        super(poolConfig, shardInfo.getJedisShardInfo());
    }

    /**
     * protected==>public.
     */
    @Override
    public void returnBrokenResourceObject(final ShardedJedis resource) {
        try {
            internalPool.invalidateObject(resource);
        } catch (Exception e) {
            throw new JedisException("Could not return the resource to the pool", e);
        }
    }
}
