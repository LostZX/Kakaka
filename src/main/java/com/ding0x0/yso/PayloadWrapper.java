package com.ding0x0.yso;

import com.ding0x0.Payload;

public class PayloadWrapper extends Payload{

    public Object object;

    public PayloadWrapper(Object object){
        this.object = object;
    }

    @Override
    public Object format() {
        return this.object;
    }
}
