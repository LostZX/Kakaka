package com.ding0x0.bootstrap;

public class TypeErrorException extends RuntimeException{
    private String retCd ;  //异常对应的返回码
    private String msgDes;  //异常对应的描述信息

    public TypeErrorException() {
        super();
    }

    public TypeErrorException(String message) {
        super(message);
        msgDes = message;
    }

    public TypeErrorException(String retCd, String msgDes) {
        super();
        this.retCd = retCd;
        this.msgDes = msgDes;
    }

    public String getRetCd() {
        return retCd;
    }

    public String getMsgDes() {
        return msgDes;
    }
}
