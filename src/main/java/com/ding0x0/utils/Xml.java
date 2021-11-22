package com.ding0x0.utils;

import com.thoughtworks.xstream.XStream;

public class Xml {
    public static String O2XML(Object object){
        XStream xStream = new XStream();
        return xStream.toXML(object);
    }
}
