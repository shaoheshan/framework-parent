package com.heshan.framework.common.VM;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author <a href="mailto:heshan664754022@gmail.com">frank</a>
 * @version V1.0
 * -XX:PermSzie=10m -XX:MaxPermSize=10m
 * @date 2016/3/30
 */
public class RuntimeConstantPoolOOM {
    public static void main(String[] args) {

        List<String> list= Lists.newArrayList();
        int i=0;
        while (true){
            list.add(String.valueOf(i++).intern());
        }
    }
}
