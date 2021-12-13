package com.ding0x0.utils;


import java.util.ArrayList;
import java.util.EnumSet;

public class Enums{
    public enum Type{
        fastjson, shiro, yso, bcel, log4j;

        public static String getAll(){
            StringBuilder sb = new StringBuilder();
            sb.append("---------------\n");
            for (Type type :
                    Type.values()) {
                sb.append("[*] ").append(type).append("\n");
            }
            sb.append("---------------");
            return sb.toString();
        }
    }

    public enum FastjsonType{
        bcel(new ArrayList<String>(){{
            add("-lf");
        }}),
        dns(new ArrayList<String>(){{
            add("-u");
        }}),
        jdbc(new ArrayList<String>(){{
            add("-u");
            add("-p");
            add("-un");
            add("-statement");
        }}),
        jndi(new ArrayList<String>(){{
            add("-u");
        }}),
        upload(new ArrayList<String>(){{
            add("-lf");
            add("-rf");
        }});

        private final ArrayList<String> args;

        private ArrayList<String> getArgs(){
            return args;
        }

        public static ArrayList<String> get(String code) {
            for (FastjsonType ele : values()) {
                if(ele.name().equals(code)) return ele.getArgs();
            }
            return null;
        }

        private FastjsonType(ArrayList<String> args){
            this.args = args;
        }

        public static String getAll(){
            StringBuilder sb = new StringBuilder();
            sb.append("--------\n");
            for (FastjsonType type :
                    FastjsonType.values()) {
                sb.append("[*] ").append(type).append("\n");
            }
            sb.append("--------");
            return sb.toString();
        }
    }

    enum Encode{
        be("base64 encode"),
        ue("url encode"),
        xe("xml encode"),
        he("html encode"),
        raw("raw echo");

        private String encode;

        private Encode(String encode){
            this.encode = encode;
        }

        private String getEncode(){
            return encode;
        }

        public static String getAll(){
            StringBuilder sb = new StringBuilder();
            for (Encode encode:
                    Encode.values()) {
                sb.append("[*]  ").append("-").append(encode).append("  ").append(encode.getEncode()).append("\n");
            }
            return sb.toString();
        }

    }


    public enum YsoGadget{
        // 太多了不枚举了，直接用yso自己的方法
    }

    public enum Args{
        k("default kPH+bIxk5D2deZiIxcaaaA=="),
        u("url or dns"),
        p("port"),
        lf("local file path"),
        rf("remote file path"),
        c("command"),
        un("username(jdbc default yso_URLDNS_http://xxx.dnslog.cn"),
        statement("statement(default com.mysql.jdbc.interceptors.ServerStatusDiffInterceptor)");

        private final String desc;

        private Args(String desc){
            this.desc = desc;
        }

        public String getDesc(){
            return desc;
        }

        public static String getYsoHelper(){
            return "--------------------------------\n" +
                    "[*] command -> -c command e.g. -c whoami\n" +
                    "[*] dirty -> -d dirty data e.g. -d 2000\n" +
                    "--------------------------------";
        }

        public static String getFastjsonHelper(){
            return "------------------------------------------------------------\n" +
                    "[*] bcel -> -lf bcel encode local file path, e.g. -lf /etc/exp.class\n" +
                    "[*] dns -> -u dnslog url, e.g. -u http://dnslog.cn\n" +
                    "[*] jdbc -> -u mysql url, -p mysql port, -un mysql username -statement jdbc statement\n" +
                    "   username default yso_URLDNS_http://xxx.dnslog.cn\n" +
                    "   statement default com.mysql.jdbc.interceptors.ServerStatusDiffInterceptor\n" +
                    "   e.g. -u 1.1.1.1 -p 3306 -username\n" +
                    "[*] jndi -> -u jndi url, e.g. -u ldap://127.0.0.1/exp\n" +
                    "[*] upload -> -lf local file path, -rf remote file path e.g. -lf /tmp/exp -rf /var/exp\n" +
                    "------------------------------------------------------------\n";
        }

        public static String getAll(){
            StringBuilder sb = new StringBuilder();
            for (Args arg:
                Args.values()) {
                sb.append("[*]  ").append("-").append(arg).append("  ").append(arg.getDesc()).append("\n");
            }
            return sb.toString();
        }
    }

    public static EnumSet<?> getAllEnums(Class clazz){
        EnumSet<?> items = EnumSet.allOf(clazz);
        return items;
    }

    public static void main(String[] args) {
        EnumSet<Type> enumSetAll = EnumSet.allOf(Type.class);
        String r = Args.getAll();
        String t = Type.getAll();
        String e = Encode.getAll();
        String f = FastjsonType.getAll();
        System.out.println(r);
        System.out.println(t);
        System.out.println(e);
        System.out.println(f);
    }
}