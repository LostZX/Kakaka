package com.ding0x0;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public interface Generate {
    public Object generatePayload() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException;
}
