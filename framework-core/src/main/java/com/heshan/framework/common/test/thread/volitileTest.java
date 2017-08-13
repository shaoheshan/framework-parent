package com.heshan.framework.common.test.thread;

/**
 * @author <a href="mailto:heshan664754022@gmail.com">Frank</a>
 * @version V1.0
 * @description
 * @date 2017/8/13 13:35
 */
public class volitileTest {

   static class  volitileTest1 extends  Thread{
        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        boolean flag;

        @Override
        public void run() {
            int i=0;
            while (!flag){
                i++;
            }
            System.out.println("i="+i);
        }

    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(2>>2);
        volitileTest1 t=new volitileTest1();
        t.start();
        Thread.sleep(1000);
        t.setFlag(true);
        Thread.sleep(2000);
        System.out.println("finish");
    }

}
