package com.bigdata.mykafka;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;
import java.util.UUID;

public class KafkaProducer {

    //声明topic
    private final static String TOPIC ="first";

    public static void main(String[] args) throws InterruptedException {

        //设置配置信息
        Properties props = new Properties();
        props.put("metadata.broker.list","centos05:9092,centos06:9092,centos07:9092");
        props.put("serializer.class","kafka.serializer.StringEncoder");
        props.put("request.required.acks","1");
        props.put("partitioner.class", "com.bigdata.mykafka.KafkaPartition"); //可配置为自己定义的partition

        //创建生产者对象
        Producer<String, String> producer = new Producer<String, String>(new ProducerConfig(props));

        //通过生产者产生数据
        for(int i = 1;i < 10000; i++) {

            String mess = new String(UUID.randomUUID() + "This is " + i + "th message");

            //通过producer的send方法发送数据
            producer.send(new KeyedMessage<String, String>(TOPIC, i + "", mess));

            //Thread.sleep(90);
        }
    }
}
