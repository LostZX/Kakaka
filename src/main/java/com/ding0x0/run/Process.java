package com.ding0x0.run;

import com.ding0x0.AbsGenerate;
import com.ding0x0.Payload;
import com.ding0x0.fastjson.GenerateFastjsonPayload;
import com.ding0x0.yso.GenerateYsoPayload;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Process {

    public CommandLine cmdline;
    //有序添加编码方式
    public Queue<String> code;

    //其他参数
    public ArrayList<String> other;

    //gadget
    public String gadget;

    //payload 类型
    public String payloadType;

    //payload参数
    public ArrayList<String> payloadParam;

    public Process(Queue<String> code, ArrayList<String> other, String gadget, String payloadType, ArrayList<String> payloadParam, CommandLine cmdline){
        this.code = code;
        this.other = other;
        this.gadget = gadget;
        this.payloadParam = payloadParam;
        this.payloadType = payloadType;
        this.cmdline = cmdline;
    }

    public Object makeObject() throws Exception{
//        String type = this.cmdline.getOptionValue(this.payloadType);
        String type = this.cmdline.getOptionValue(this.payloadType);
        AbsGenerate generate = null;
        switch (this.payloadType){
            case "fastjson":
                generate = new GenerateFastjsonPayload(this.payloadParam, type);
                break;
            case "yso":
                String cmd = this.cmdline.getOptionValue(this.payloadParam.get(0));
                generate = new GenerateYsoPayload(type, cmd);
        }
        return generate.generatePayload();
    }

    public static void main(String[] args) throws Exception {

    }
}
