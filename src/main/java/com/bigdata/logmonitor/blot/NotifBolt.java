package com.bigdata.logmonitor.blot;

import com.bigdata.logmonitor.bean.Message;
import com.bigdata.logmonitor.bean.Record;
import com.bigdata.logmonitor.utils.Monitorhandler;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

/**
 * 调用通知方法
 */
public class NotifBolt extends BaseRichBolt {
    OutputCollector outputCollector;

    private static Logger logger = Logger.getLogger(NotifBolt.class);

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.outputCollector = outputCollector;
    }

    @Override
    public void execute(Tuple tuple) {
        Message message = (Message) tuple.getValueByField("message");
        String appId = (String) tuple.getValueByField("appId");

        //调用通知方法，进行通知
        Monitorhandler.notify(appId,message);

        //定义一个record，用于保存信息
        Record record = new Record();
        //将message信息转换为record
        try {
            BeanUtils.copyProperties(record,message);
            //将转换的record发送到下一个bolt
            outputCollector.emit(new Values(record));
        } catch (Exception e) {
            //出现异常不处理
        }
        //手动调用ack
        outputCollector.ack(tuple);

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("record"));
    }
}
