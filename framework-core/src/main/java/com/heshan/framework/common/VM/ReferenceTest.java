package com.heshan.framework.common.VM;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author <a href="mailto:heshan664754022@gmail.com">frank</a>
 * @version V1.0
 * Java 中一共有 4 种类型的引用 : StrongReference、 SoftReference、 WeakReference 以及 PhantomReference (传说中的幽灵引用 呵呵),
这 4 种类型的引用与 GC 有着密切的关系,  让我们逐一来看它们的定义和使用场景 :
 * @date 2016/3/31
 */
public class ReferenceTest{

    public void strongReference() {
        Object referent = new Object();

        /**
         * 通过赋值创建 StrongReference
         */
        Object strongReference = referent;

        referent = null;
        System.gc();

        /**
         * StrongReference 在 GC 后不会被回收
         */
        //Assert.assertNull(strongReference);
    }
   // @Test
    public void weakReference() {
        Object referent = new Object();
        WeakReference<Object> weakRerference = new WeakReference<Object>(referent);

       // Assert.assertSame(referent, weakRerference.get());

        referent = null;
        System.gc();

        /**
         * 一旦没有指向 referent 的强引用, weak reference 在 GC 后会被自动回收
         */
       // Assert.assertNull(weakRerference.get());
    }
   // @Test
    public void weakHashMap() throws InterruptedException {
        Map<Object, Object> weakHashMap = new WeakHashMap<Object, Object>();
        Object key = new Object();
        Object value = new Object();
        weakHashMap.put(key, value);

      //  Assert.assertTrue(weakHashMap.containsValue(value));

        key = null;
        System.gc();

        /**
         * 等待无效 entries 进入 ReferenceQueue 以便下一次调用 getTable 时被清理
         */
        Thread.sleep(1000);

        /**
         * 一旦没有指向 key 的强引用, WeakHashMap 在 GC 后将自动删除相关的 entry
         */
        //Assert.assertFalse(weakHashMap.containsValue(value));
    }
   // @Test
    public void softReference() {
        Object referent = new Object();
        SoftReference<Object> softRerference = new SoftReference<Object>(referent);

       // Assert.assertNotNull(softRerference.get());

        referent = null;
        System.gc();

        /**
         *  soft references 只有在 jvm OutOfMemory 之前才会被回收, 所以它非常适合缓存应用
         */
       // Assert.assertNotNull(softRerference.get());
    }


}
