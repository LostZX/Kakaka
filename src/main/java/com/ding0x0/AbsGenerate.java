package com.ding0x0;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class AbsGenerate implements Generate{

    public ArrayList<String> params;
    public String classname;

    public Class<?>[] getClassArray(){
        Class<?>[] classes = new Class[this.params.size()];
        for (int i = 0; i < this.params.size(); i++) {
            classes[i] = String.class;
        }
        return classes;
    }


    @Override
    public Object generatePayload() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return null;
    }
}
