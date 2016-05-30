package com.heshan.framework.common.VM;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author <a href="mailto:heshan664754022@gmail.com">frank</a>
 * @version V1.0
 * @date 2016/3/30
 *  -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
 * -Xms20M -Xmx20M -XX:+heapDumpOnOutOfMemoryError
 */
public class HeapOOM {

    static  class OOMObject{

    }
    public static void main(String[] args) {
        List<OOMObject> list= Lists.newArrayList();
        while (true){
            list.add(new OOMObject());
        }
    }
}
