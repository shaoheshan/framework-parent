package com.heshan.framework.redis;

/**
 * RedisConstant.
 *
 * @version : Ver 1.0
 */
public class RedisConstant {

	//public static String redisConf="10.4.0.20:6379,101.200.190.144:6379:test123";
	public static String redisConf="101.200.190.144:6379";
	//public static String redisConf="10.4.0.20:6379,10.1.1.163:6379";
	public static String redisTook="true";

	public static String getRedisConf() {
		return redisConf;
	}

	public static String getRedisTook() {
		return redisTook;
	}


}
