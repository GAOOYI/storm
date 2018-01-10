package com.bigdata.wordcount;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IBasicBolt;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.IWindowedBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.util.HashMap;
import java.util.Map;

public class MyCountBolt extends BaseRichBolt{
    OutputCollector outputCollector;
    Map<String,Integer> wordcount = new HashMap<String, Integer>();

    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.outputCollector = outputCollector;
    }

    public void execute(Tuple tuple) {
        String word = tuple.getString(0);
        int count = tuple.getInteger(1);
        if (wordcount.containsKey(word)){
            int num = wordcount.get(word) + count;
            wordcount.put(word,num);
        }else {
            wordcount.put(word,1);
        }
        System.out.println("TheadId:" + Thread.currentThread().getId() + ";  word:"+word+";  num:"+wordcount.get(word));
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}