package com.heshan.framework.common.redis.config;

import com.heshan.framework.common.string.EmptyUtils;

/**
 * @author <a href="mailto:heshan664754022@gmail.com">frank</a>
 * @version V1.0
 * @date 2016/3/11
 */
public class RedisContext {

    /**
     * 租户变量
     */
    private final static InheritableThreadLocal<String> TENANT_LOCAL = new InheritableThreadLocal<String>();

    /**
     * 获取租户
     *
     * @return
     */
    public static String getTenant() {
        return EmptyUtils.isEmpty(TENANT_LOCAL.get()) ? "" : TENANT_LOCAL.get().trim() + "_";
    }

    /**
     * 设置租户
     *
     * @param tenant
     */
    public static void setTenant(String tenant) {
        if (!EmptyUtils.isEmpty(tenant)) {
            TENANT_LOCAL.set(tenant);
        }
    }

    /**
     * ɾ��.
     *
     */
    public static void remove() {
        TENANT_LOCAL.remove();
    }
}
