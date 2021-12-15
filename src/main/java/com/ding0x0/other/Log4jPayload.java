package com.ding0x0.other;

import com.ding0x0.Payload;

public class Log4jPayload extends Payload {

    public String jndi;
    public String property;

    public Log4jPayload(String jndi, String property){
        this.jndi = jndi;
        this.property = property;
    }

    @Override
    public Object format() throws Exception{
        return null;
    }
}
