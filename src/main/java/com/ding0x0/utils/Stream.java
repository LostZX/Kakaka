package com.ding0x0.utils;

import java.io.*;

public class Stream {
    public static String O2B64S(Object object) throws IOException {
        byte[] bytes = O2B(object);
        return Code.base64Encode(bytes);
    }

    public static byte[] O2B(Object object) throws IOException{
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(object);
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] F2B(String path) throws IOException{
        File f = new File(path);
        byte[] bytes = new byte[(int) f.length()];
        FileInputStream fileInputStream = new FileInputStream(f);
        fileInputStream.read(bytes);
        fileInputStream.close();
        return bytes;
    }

    public static String F2S(String path) throws IOException{
        byte[] bytes = F2B(path);
        return new String(bytes);
    }

}
