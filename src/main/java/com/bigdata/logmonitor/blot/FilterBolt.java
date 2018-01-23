package com.bigdata.logmonitor.blot;

import com.bigdata.logmonitor.bean.Message;
import com.bigdata.logmonitor.utils.Monitorhandler;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

/**
 * 过滤其中不需要的数据
 *
 *在BaseBasicBolt中，BasicOutputCollector在emit数据的时候，会自动和输入的tuple相关联，而在execute方法结束的时候那个输入tuple会被自动ack。
 在使用BaseRichBolt需要在emit数据的时候，显示指定该数据的源tuple要加上第二个参数anchor tuple，以保持tracker链路，即collector.emit(oldTuple, newTuple);
 并且需要在execute执行成功后调用OutputCollector.ack(tuple), 当失败处理时，执行OutputCollector.fail(tuple);
 *
 */
public class FilterBolt extends BaseRichBolt{
    OutputCollector outputCollector;

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.outputCollector = outputCollector;
    }

    @Override
    public void execute(Tuple tuple) {
        //获取spout发送过来的数据
        String line = tuple.getString(0);
        //如果是kafkaspout发送过来的为byte数据
        //byte[] value = (byte[]) tuple.getValue(0);
        //再将byte数据转换为字符串
        //String line = value.toString();

        //解析发送过来的数据，将字符串转换为一个bean对象
        Message message = Monitorhandler.parser(line);

        if (message == null)
            return;
        //对日志进行规制判定，看看是否触发规则
        if (Monitorhandler.trigger(message)){
            System.out.println("FilterBolt发送数据"+message.toString());
            outputCollector.emit(new Values(message.getAppId(),message));
        }

        //调用调度方法进行调度
        Monitorhandler.scheduleLoad();

        //手动调用ack方法
        outputCollector.ack(tuple);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("appId","message"));
    }
}
