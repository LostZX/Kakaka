package com.ding0x0.utils;

import sun.misc.BASE64Encoder;

import java.net.URLEncoder;
import java.util.Base64;

public class Code {
    public static String base64Encode(byte[] bytes){
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static String base64EncodeForSun(byte[] bytes){
        BASE64Encoder base64Encoder = new BASE64Encoder();
        return base64Encoder.encode(bytes);
    }

    public static String urlEncode(String payload){
        return URLEncoder.encode(payload);
    };
}
