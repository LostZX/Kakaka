package com.ding0x0.utils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Util {
    public static List<String> getGadgetList() throws NoSuchFieldException, IllegalAccessException {
        String path = Util.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        JarFile jarFile = null;
        try {
            jarFile = new JarFile(path);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        List<String> gadgetList = new ArrayList<String>();
        Enumeration<JarEntry> entrys = jarFile.entries();

        while (entrys.hasMoreElements()) {
            JarEntry jarEntry = entrys.nextElement();
            if (jarEntry.getName().startsWith("com/ding0x0/yso/payloads/")){
                String name = jarEntry.getName();
                if (name.contains("CommonsBeanutils") || name.contains("CommonsCollections")){
                    String gadget = name.replace("com/ding0x0/yso/payloads/","").replace(".class","");
                    gadgetList.add(gadget);
                }
            }
        }
        return gadgetList;
    }
}
