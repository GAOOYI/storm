package com.bigdata.mykafka;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KafkaConsumer implements Runnable {
    private final static String TOPIC = "first";

    private int consumerNum;
    private KafkaStream<byte[], byte[]> stream;

    public KafkaConsumer(int i, KafkaStream<byte[], byte[]> messageAndMetadata) {
        this.consumerNum = i;
        this.stream = messageAndMetadata;
    }

    public void run() {
        String com = "消费者：" + consumerNum;
        ConsumerIterator<byte[], byte[]> iterator = stream.iterator();
        while (iterator.hasNext()){
            MessageAndMetadata<byte[], byte[]> metadata = iterator.next();
            String data = new String(metadata.message());
            int partition = metadata.partition();
            System.out.println(com + ";  分片：" + partition + ":  data:" + data);
        }


    }

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("group.id", "Consumer_01");
        props.put("zookeeper.connect", "centos02:2181,centos03:2181,centos04:2181");
        //props.put("auto.offset.reset", "largest");
        props.put("auto.commit.interval.ms", "1000");
        //props.put("partition.assignment.strategy", "roundrobin");
        ConsumerConfig config = new ConsumerConfig(props);

        //创建consumer
        ConsumerConnector consumer = Consumer.createJavaConsumerConnector(config);

        //定义一个map集合
        Map<String,Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(TOPIC, 3);

        //Map<String, List<KafkaStream<byte[], byte[]>> 中String是topic， List<KafkaStream<byte[], byte[]>是对应的流
        Map<String , List<KafkaStream<byte[],byte[]>>> topicStreamMap = consumer.createMessageStreams(topicCountMap);

        //取出topic对应的流
        List<KafkaStream<byte[], byte[]>> streams = topicStreamMap.get(TOPIC);

        //创建一个线程池，多线程去读取数据
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        //读取数据
        for(int i = 0; i < streams.size(); i++){
            executorService.execute(new KafkaConsumer(i,streams.get(i)));
        }
    }
}
