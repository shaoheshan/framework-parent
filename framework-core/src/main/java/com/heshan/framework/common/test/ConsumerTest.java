package com.heshan.framework.common.test;

import com.aliyun.openservices.ons.api.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * @author <a href="mailto:heshan664754022@gmail.com">Frank</a>
 * @version V1.0
 * @description
 * @date 2016/7/20 17:26
 */
public class ConsumerTest {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.ConsumerId, "CID_Frank_testMq");
        properties.put(PropertyKeyConst.AccessKey, "YFjtaLkeqeC2mBcp");
        properties.put(PropertyKeyConst.SecretKey, "oc9KtGrfIXoaosQ3DaliZB0oolTKdy ");
        properties.put(PropertyKeyConst.ConsumeTimeout,"20");

        Consumer consumer = ONSFactory.createConsumer(properties);
        consumer.subscribe("frank_testMq", "*", new MessageListener() {
            public Action consume(final Message message, ConsumeContext context) {
                System.out.println("消费时间:"+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
                System.out.println("Key: " + new String(message.getKey()));
                System.out.println("meassageId="+ message.getMsgID());
                System.out.println("Receive: " + new String(message.getBody()));
                return Action.CommitMessage;
            }
        });
        consumer.start();
        System.out.println("Consumer Started");
    }
}
