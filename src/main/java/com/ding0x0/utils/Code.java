package com.ding0x0.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Base64;

public class Code {
    public static String base64Encode(byte[] bytes){
        return Base64.getEncoder().encodeToString(bytes);
    }
    public static byte[] base64Decode(String in){return Base64.getDecoder().decode(in);}

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
