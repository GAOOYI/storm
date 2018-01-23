import com.bigdata.logmonitor.bean.Record;
import com.bigdata.logmonitor.utils.Monitorhandler;
import redis.clients.jedis.Jedis;

import java.util.List;

public class test {
    public static void main(String[] args) {
        Record record = new Record();
        record.setId(1);
        record.setAppId(01);
        record.setIsEmail(1);

        Monitorhandler.save(record);

        Jedis jedis = new Jedis("127.0.0.1",6379);

        String response = jedis.ping();

        System.out.println(response);

        List<String> records = jedis.lrange("record", 0, -1);
        for (String s:records
             ) {
            System.out.println(s);
        }

    }
}
