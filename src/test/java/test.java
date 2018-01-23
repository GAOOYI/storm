import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;

public class test {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("centos01",6379);
        //jedis.del("record");

        List<String> record = jedis.lrange("record", 0, -1);
        for (String s:record){
            System.out.println(s);
        }
    }

    @Test
    public void testString(){
        String s = "1$$$$$2";

        String[] ss = s.split("[$][$][$][$][$]");

        System.out.println(ss.length);
        for (String sss:ss
             ) {
            System.out.println(sss);
        }
    }
}
