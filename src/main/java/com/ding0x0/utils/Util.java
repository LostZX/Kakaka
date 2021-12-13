package com.ding0x0.utils;

import com.ding0x0.yso.payloads.Gadget;
import org.reflections.Reflections;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Util {
    public static List<String> getGadgetList() throws NoSuchFieldException, IllegalAccessException {
        List<String> classNames = new ArrayList<>();
        Set<Class<?>> classes = new Reflections(Gadget.class.getPackage().getName()).getTypesAnnotatedWith(Gadget.class);
        for (Class c :
                classes) {
            classNames.add(c.getName());
        }
        return classNames;
    }
}
