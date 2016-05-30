/**
 * <p>RedisObject.java</p>
 *   
 */
package com.heshan.framework.common.redis.serializer;

import com.heshan.framework.common.redis.exception.RedisException;

import java.util.Arrays;

/**
 * 
 * @description: TODO add description
 * @version Ver 1.0
 * @Date 2012-9-12 下午04:48:09
 */
public class RedisObject {
	public final int MAX_SIZE = 20 * 1024 * 1024;

	private final int flags;
	private final byte[] data;

	public byte[] getData() {
		return data;
	}

	public int getFlags() {
		return flags;
	}

	public RedisObject(final int flags, final byte[] data) {
		if (data.length > MAX_SIZE) {
			throw new RedisException("Stored data amount is too large [" + data.length + "]");
		}
		this.flags = flags;
		this.data = data;
	}

	@Override
	public String toString() {
		return "RedisObject [MAX_SIZE=" + MAX_SIZE + ", flags=" + flags + ", data=" + Arrays.toString(data) + "]";
	}

}
