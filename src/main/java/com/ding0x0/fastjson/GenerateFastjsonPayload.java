package com.ding0x0.fastjson;

import com.ding0x0.AbsGenerate;
import com.ding0x0.Generate;
import com.ding0x0.Payload;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class GenerateFastjsonPayload extends AbsGenerate {

    @Override
    public Object generatePayload() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> clazz = Class.forName("com.ding0x0.fastjson." + classname + "Payload");
        Constructor constructor = clazz.getConstructor(getClassArray());
        if (this.params.isEmpty()){
            return constructor.newInstance();
        }
        return constructor.newInstance(this.params.toArray());
    }


    public GenerateFastjsonPayload(ArrayList<String> params, String classname){
        this.params = params;
        this.classname = classname;
    }
}
