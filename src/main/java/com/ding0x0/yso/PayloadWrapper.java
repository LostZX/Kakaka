package com.ding0x0.yso;

import com.ding0x0.Payload;

public class PayloadWrapper implements Payload {

    public Object object;

    public PayloadWrapper(Object object){
        this.object = object;
    }

    @Override
    public String format() {
        return null;
    }
}
