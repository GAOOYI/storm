package com.bigdata.logmonitor;

import com.bigdata.logmonitor.blot.FilterBolt;
import com.bigdata.logmonitor.blot.NotifBolt;
import com.bigdata.logmonitor.blot.SaveBolt;
import com.bigdata.logmonitor.spout.RandomSpout;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

public class MylogMonitorMain {

    public static void main(String[] args) throws InvalidTopologyException, AuthorizationException, AlreadyAliveException {
        //创建一个TopologyBuilder
        TopologyBuilder topologyBuilder = new TopologyBuilder();

        //设置spout
        topologyBuilder.setSpout("mysplot",new RandomSpout(),2);
        //设置blot
        topologyBuilder.setBolt("filerbolt",new FilterBolt(),2).shuffleGrouping("mysplot");
        topologyBuilder.setBolt("notifyBolt",new NotifBolt(),4).fieldsGrouping("filerbolt",new Fields("appId"));
        topologyBuilder.setBolt("saveBolt",new SaveBolt(),4).shuffleGrouping("notifyBolt");

        //设置conf，指定work数量
        Config config = new Config();
        config.setNumWorkers(2);

        if(args.length > 0){
            //提交为集群模式
            StormSubmitter.submitTopology(args[0],config,topologyBuilder.createTopology());
        }else{
            //提交为本地模式
            LocalCluster localCluster = new LocalCluster();
            localCluster.submitTopology("logmonitor",config,topologyBuilder.createTopology());
        }
    }

}
