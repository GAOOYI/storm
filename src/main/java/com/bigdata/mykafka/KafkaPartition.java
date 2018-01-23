package com.bigdata.mykafka;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;
import org.apache.log4j.Logger;


public class KafkaPartition implements Partitioner {

    //注意：必须添加logger和构造函数，否则在producer中无法调用partition这个类

    private static Logger logger = Logger.getLogger(KafkaPartition.class);

    public KafkaPartition(VerifiableProperties props) {
    }

    public int partition(Object key, int numPartitions) {
        //默认的分区方法
        return Integer.parseInt(key.toString()) % numPartitions;
    }
}
