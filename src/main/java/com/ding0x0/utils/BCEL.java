package com.ding0x0.utils;

import com.sun.org.apache.bcel.internal.classfile.Utility;

import java.io.IOException;

public class BCEL {
    public static String encode(byte[] bytes) throws IOException {
        return "$$BCEL$$" + Utility.encode(bytes, true);
    }
}
