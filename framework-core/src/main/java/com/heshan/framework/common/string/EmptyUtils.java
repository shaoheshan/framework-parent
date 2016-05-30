package com.heshan.framework.common.string;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:heshan664754022@gmail.com">frank</a>
 * @version V1.0
 * @date 2016/3/11
 */
public abstract class EmptyUtils {

    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof Object[]) {
            return isEmpty((Object[]) obj);
        } else if (obj instanceof String) {
            return isEmpty((String) obj);
        } else if (obj instanceof String[]) {
            return isEmpty((String[]) obj);
        } else if (obj instanceof Map<?, ?>) {
            return isEmpty((Map<?, ?>) obj);
        } else if (obj instanceof List<?>) {
            return isEmpty((List<?>) obj);
        }
        return false;
    }

    public static boolean isEmpty(Object[] obj) {
        return obj == null || obj.length == 0;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isEmpty(String[] str) {
        return str == null || str.length == 0;
    }

    public static boolean isEmpty(Map<?, ?> map) {

        return map == null || map.isEmpty();
    }

    public static boolean isEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }
    public static boolean hasLength(CharSequence str) {
        return (str != null && str.length() > 0);
    }
    /**
     * Check that the given String is neither {@code null} nor of length 0.
     * Note: Will return {@code true} for a String that purely consists of whitespace.
     * @param str the String to check (may be {@code null})
     * @return {@code true} if the String is not null and has length
     * @see #hasLength(CharSequence)
     */
    public static boolean hasLength(String str) {
        return hasLength((CharSequence) str);
    }
    /**
     * Trim leading and trailing whitespace from the given String.
     * @param str the String to check
     * @return the trimmed String
     * @see java.lang.Character#isWhitespace
     */
    public static String trimWhitespace(String str) {
        if (!hasLength(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        while (sb.length() > 0 && Character.isWhitespace(sb.charAt(0))) {
            sb.deleteCharAt(0);
        }
        while (sb.length() > 0 && Character.isWhitespace(sb.charAt(sb.length() - 1))) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
    public static void main(String[] args) {
        System.out.println(EmptyUtils.isEmpty(" "));
        String str=null;
        System.out.println(EmptyUtils.isEmpty("  "));
        System.out.println(EmptyUtils.trimWhitespace(" 111 11 "));
        System.out.println(EmptyUtils.trimWhitespace(str));

    }
}
