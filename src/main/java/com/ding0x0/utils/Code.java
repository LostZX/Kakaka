package com.ding0x0.utils;

import com.thoughtworks.xstream.XStream;
import org.apache.commons.lang3.StringEscapeUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Base64;

public class Code {
    public static String base64Encode(Object o){
        String result = null;
        try {
            o = (byte[]) o;
            result = Base64.getEncoder().encodeToString((byte[]) o);
        }catch (ClassCastException e){
            try {
                o = (String) o;
                result = Base64.getEncoder().encodeToString(((String) o).getBytes());
            }catch (ClassCastException e1){
                try {
                    result = Base64.getEncoder().encodeToString(Stream.O2B(o));
                }catch (Exception e2){
                    e2.printStackTrace();
                }
            }
        }
        return result;
    }
    public static byte[] base64Decode(String in){return Base64.getDecoder().decode(in);}

    public static String HTMLEncode(String in){
        return  StringEscapeUtils.escapeHtml4(in);
    }

    public static String O2XML(Object object){
        XStream xStream = new XStream();
        return xStream.toXML(object);
    }

    public static String base64EncodeForSun(byte[] bytes){
        BASE64Encoder base64Encoder = new BASE64Encoder();
        return base64Encoder.encode(bytes);
    }

    public static byte[] base64DecodeForSun(String in) throws IOException {
        BASE64Decoder base64Decoder = new BASE64Decoder();
        return base64Decoder.decodeBuffer(in);
    }

    public static String urlEncode(String payload){
        return URLEncoder.encode(payload);
    };
}
