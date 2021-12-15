package com.ding0x0.other;

import com.ding0x0.AbsGenerate;
import java.rmi.server.UnicastRemoteObject;

public class GenerateLog4jPayload extends AbsGenerate {

    public String jndi;
    public String property;

    public GenerateLog4jPayload(String jndi){
        this.jndi = jndi;
    }
}
