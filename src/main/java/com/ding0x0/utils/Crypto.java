package com.ding0x0.utils;

import org.apache.shiro.crypto.CipherService;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import sun.misc.BASE64Encoder;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Crypto {
    public static String shiroEncrypt(byte[] bytes, String encryptKey){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        byte[] key = encryptKey == null ? cookieRememberMeManager.getCipherKey() : encryptKey.getBytes();
        CipherService cipherService = cookieRememberMeManager.getCipherService();
        ByteSource source = cipherService.encrypt(bytes, key);
        return source.toBase64();
    }

    public static String shiroEncrypt(byte[] bytes){
        return shiroEncrypt(bytes, null);
    }
}
