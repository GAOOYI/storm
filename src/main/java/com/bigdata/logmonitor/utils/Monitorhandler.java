package com.bigdata.logmonitor.utils;

import com.bigdata.logmonitor.bean.*;
import com.bigdata.logmonitor.mail.MailInfo;
import com.bigdata.logmonitor.mail.MessageSender;
import org.apache.storm.shade.org.apache.commons.lang.StringUtils;
import redis.clients.jedis.Jedis;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Monitorhandler {
    //连接redis
    private static Jedis jedis;
    static {
        jedis = new Jedis("127.0.0.1",6379);
        jedis.del("record");
    }
    //合法的用户，定义一个list，用来封装所有的应用信息
    private static List<App> applist;
    //定义一个规则信息Map，其中appId为Key，以该appId下的所有rule为Value
    private static Map<String, List<Rule>> ruleMap;
    //定义一个用户map，key为appid，通过数据库获得
    private static Map<String,List<User>> userMap;
    //定义一个时间格式
    private  static  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    /**
     * 通过获得的字符串，转换为一个message对象
     * @param line
     * @return
     *
     *
     */
    public static Message parser(String line) {
        //1$$$$$error: Caused by: java.lang.NoClassDefFoundError: com/starit/gejie/dao/SysNameDao
        String[] split = line.split("//$//$//$//$");
        if (split.length != 2){
            return null;
        }
        if(StringUtils.isBlank(split[0]) || StringUtils.isBlank(split[1])){
            return null;
        }

        //判断该条日志信息是否授权
        if(appIdisValid(split[1].trim())){
            Message message = new Message();
            message.setAppId(split[0].trim());
            message.setLine(split[1].trim());
            return message;
        }

        return null;
    }

    /**
     * 判断该id是否合法的。数据来源于数据库或者redis
     * @param appid
     * @return
     */
    private static boolean appIdisValid(String appid) {
        //使用try抛出异常，防止appid不是一个合法的数字id
        try {
            for (App app:applist
                    ) {
                if (app.getId() == Integer.parseInt(appid)){
                    return true;
                }
            }
        }catch (Exception e){
            return false;
        }
        return false;
    }

    /**
     * 对传过来的日志进行监控，判断是否合法，触发规则
     * @param message
     * @return
     */
    public static boolean trigger(Message message) {
        //规则信息来自于数据库或者redis，如果规则为空，则需要加载规则模型
        if (ruleMap == null){
            //调用load方法加载
            load();
        }
        //从规则数据中拿出appid的规则
        List<Rule> rules = ruleMap.get(message.getAppId());
        for (Rule rule:rules
             ) {
            if (message.getLine().contains(rule.getKeyword())){
                message.setKeyword(rule.getKeyword());
                message.setRuleId(rule.getId() + "");
                return true;
            }
        }
        return false;
    }

    /**
     * 从数据库中加载所需的信息
     * 链接数据库，mybatis
     */
    public static void load(){
        if (applist == null){

        }


    }

    /**
     * 通知方法，通过邮件通知
     * @param appId
     * @param message
     */
    public static void notify(String appId, Message message) {
        //通过appId获取到用户的信息
        List<User> users = getUserByappId(appId);

        //调用发送sendMail方法
        if (sendMail(appId, users, message)){
            message.setIsEmail(1);
        }
        message.setIsPhone(1);
    }

    /**
     * 发送emali模块
     * @param appId
     * @param users
     * @param message
     * @return
     */

    private static boolean sendMail(String appId, List<User> users, Message message) {
        //加载所有的receiver
        List<String> receiver = new ArrayList<String>();
        for (User user :users
                ) {
            receiver.add(user.getEmail());
        }

        //通过appid获取appname
        for (App app:applist
             ) {
            if (app.getId() == Integer.parseInt(appId)){
                message.setAppName(app.getName());
                break;
            }
        }

        //调用MessageSender中的sendMail方法发送邮件
        if (receiver.size() > 0){
            String context = "系统【" + message.getAppName() + "】在" + sdf.format(new Date())
                    + " 触发规则：" + message.getRuleId()
                    + ", 过滤关键字为：" + message.getKeyword()
                    + ", 错误内容为：" + message.getLine();
            MailInfo mailInfo = new MailInfo("日志监控",context, receiver, null);
            return MessageSender.sendMail(mailInfo);
        }
        return false;
    }

    /**
     * 获取到appId对应的users
     * @param appId
     * @return
     */
    private static List<User> getUserByappId(String appId) {
        //用过一个userMap获取到User
        return userMap.get(appId);

    }

    /**
     * 链接数据库将错误信息保存到redis
     * @param record
     */
    public static void save(Record record) {
        jedis.lpush("record",record.toString());
    }

}
