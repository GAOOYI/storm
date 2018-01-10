package com.bigdata.wordcount;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

public class wordCountTopoMain {
    public static void main(String[] args) throws InvalidTopologyException, AuthorizationException, AlreadyAliveException {
        //创建一个TopologyBuilder
        TopologyBuilder topologyBuilder = new TopologyBuilder();

        //设置spout
        topologyBuilder.setSpout("mysplot1",new MySpot(),2);
        //设置blot
        topologyBuilder.setBolt("mybolt1",new MysplitBolt(),2).shuffleGrouping("mysplot1");
        topologyBuilder.setBolt("mybolt2",new MyCountBolt(),4).fieldsGrouping("mybolt1",new Fields("word"));

        //设置conf，指定work数量
        Config config = new Config();
        config.setNumWorkers(2);

        //提交为本地模式
        LocalCluster localCluster = new LocalCluster();
        localCluster.submitTopology("wordcount",config,topologyBuilder.createTopology());
        //提交为集群模式
        //StormSubmitter.submitTopology("wordcount",config,topologyBuilder.createTopology());
    }
}
