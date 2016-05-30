/**
 * <p>StringUtils.java</p>
 *   
 */
package com.heshan.framework.common.redis.serializer;

import java.util.Collection;

/**
 * 
 * @description: TODO add description
 * @version Ver 1.0
 * @Date 2012-9-12 下午04:48:09
 */
final class StringUtils {

	public static String join(Collection<String> keys, String delimiter) {
		StringBuilder sb = new StringBuilder();
		for (String key : keys) {
			sb.append(key);
			sb.append(delimiter);
		}
		return sb.toString();
	}

	public static boolean isJsonObject(String s) {
		if (s.startsWith("{") || s.startsWith("[") || s.equals("true") || s.equals("false") || s.equals("null")) {
			return true;
		}
		try {
			new Integer(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}