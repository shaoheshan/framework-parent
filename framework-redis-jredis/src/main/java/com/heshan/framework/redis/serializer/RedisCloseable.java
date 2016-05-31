/**
 * <p>RedisCloseable.java</p>
 *   
 */
package com.heshan.framework.redis.serializer;

import java.io.Closeable;

import org.apache.log4j.Logger;

/**
 * 
 * @description: TODO add description
 * @version Ver 1.0
 * @Date 2012-9-12 下午04:48:09
 */
public final class RedisCloseable {

	transient static Logger logger = Logger.getLogger(RedisCloseable.class);

	private RedisCloseable() {
	}

	public static void close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (Exception e) {
				logger.info("Unable to close " + closeable, e);
			}
		}
	}
}