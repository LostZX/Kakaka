package com.ding0x0.yso;

import com.ding0x0.AbsGenerate;
import com.ding0x0.Payload;
import com.ding0x0.yso.payloads.ObjectPayload;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class GenerateYsoPayload extends AbsGenerate {

    public String type;
    public String cmd;

    public GenerateYsoPayload(String type, String cmd) {
        this.cmd = cmd;
        this.type = type;
    }

    @Override
    public Object generatePayload() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> clazz = Class.forName("com.ding0x0.yso.payloads." + this.type);
        ObjectPayload obj = (ObjectPayload) clazz.newInstance();
        Object payload = null;
        try {
            payload = obj.getObject(this.cmd);
        }catch (Exception ignore){}
        Object object = new PayloadWrapper(payload);
        return object;
    }

}
