package com.bigdata.logmonitor.blot;

import com.bigdata.logmonitor.bean.Record;
import com.bigdata.logmonitor.utils.Monitorhandler;
import org.apache.log4j.Logger;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.util.Map;

/**
 * 将record信息保存到数据库
 */
public class SaveBolt extends BaseRichBolt {

    private static Logger logger = Logger.getLogger(SaveBolt.class);

    OutputCollector outputCollector;
    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.outputCollector = outputCollector;
    }

    @Override
    public void execute(Tuple tuple) {
        Record record = (Record) tuple.getValueByField("record");
        Monitorhandler.save(record);
        outputCollector.ack(tuple);

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
