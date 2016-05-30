package com.heshan.framework.common.test;

import com.google.common.base.Function;
import com.google.common.collect.MapMaker;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:heshan664754022@gmail.com">frank</a>
 * @version V1.0
 * @date 2016/3/24
 */
public class GoogleColTestMain {

    /**
     * @param args
     */
    public static void main(String[] args) {
        /**
         * expiration(3, TimeUnit.SECONDS)���ó�ʱʱ��Ϊ3��
         */
        ConcurrentMap<String , String> map = new MapMaker().concurrencyLevel(32).softKeys().weakValues()
                .expiration(3, TimeUnit.SECONDS).makeComputingMap(
                        /**
                         * �ṩ��Map���治������get��������Զ����뵽Map�Ĺ���
                         * ���Խ�����ķ���ֵ�ŵ���Ӧ��key��value��
                         */
                        new Function<String, String>() {
                            public String apply(String s) {
                                return "creating " + s + " -> Object";
                            }
                        }
                );

        map.put("a","testa");
        map.put("b","testb");

        System.out.println(map.get("a"));
        System.out.println(map.get("b"));
        System.out.println(map.get("c"));

        try {
            // 4��󣬴��ڳ�ʱʱ�䣬����ʧЧ��
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(map.get("a"));
        System.out.println(map.get("b"));
        System.out.println(map.get("c"));
    }

}

