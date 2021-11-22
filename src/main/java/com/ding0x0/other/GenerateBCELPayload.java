package com.ding0x0.other;

import com.ding0x0.AbsGenerate;
import com.ding0x0.utils.BCEL;
import com.ding0x0.utils.Stream;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class GenerateBCELPayload extends AbsGenerate {
    String lfile;

    public GenerateBCELPayload(String lfile){
        this.lfile = lfile;
    }

    @Override
    public Object generatePayload() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        return new bcelPayload(this.lfile);
    }
}
