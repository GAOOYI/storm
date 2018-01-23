package com.bigdata.logmonitor.spout;

import org.apache.storm.spout.Scheme;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Scheme主要负责定义如何从消息流中解析所需数据。
 * 其实就是返回了String和声明了字段”str”，只要在方法里自定义对字节流的操作，并声明了字段，就可以自定义自己的Scheme了。
 */
public class StringScheme implements Scheme{
    private static final Charset UTF8_CHARSET = StandardCharsets.UTF_8;
    public static final String STRING_SCHEME_KEY = "line";

    public List<Object> deserialize(byte[] bytes) {
        return new Values(bytes.toString());
    }

    public static String deserializeString(ByteBuffer string) {
        if (string.hasArray()) {
            int base = string.arrayOffset();
            return new String(string.array(), base + string.position(), string.remaining());
        } else {
            return new String(Utils.toByteArray(string), UTF8_CHARSET);
        }
    }


    @Override
    public List<Object> deserialize(ByteBuffer byteBuffer) {
        return new Values(byteBuffer.toString());
    }

    public Fields getOutputFields() {
        return new Fields(STRING_SCHEME_KEY);
    }
}
