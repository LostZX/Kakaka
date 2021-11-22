package com.ding0x0.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;

public class Util {
    public static ArrayList<String> getGadgetList() throws IOException {
        String pkg = "com.ding0x0.yso.payloads";
        ArrayList<String> fileList = new ArrayList<>();
        Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(pkg.replace(".","/"));
        System.out.println(dirs);
        File dir = new File(dirs.nextElement().getPath());
        System.out.println(dir);
        File[] files = dir.listFiles();
        for (File f :
                files) {
            if (f.getName().startsWith("CommonsCollections") || f.getName().startsWith("CommonsBeanutils")){
                fileList.add(f.getName().replace(".class",""));
            }
        }
        return fileList;
    }


    public static void main(String[] args) throws IOException {
        ArrayList<String> a = getGadgetList();
        System.out.println(a);
    }
}
