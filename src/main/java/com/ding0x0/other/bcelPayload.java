package com.ding0x0.other;

import com.ding0x0.Payload;
import com.ding0x0.utils.BCEL;
import com.ding0x0.utils.Stream;

import java.io.IOException;

public class bcelPayload extends Payload {

    String lf;

    public bcelPayload(String lf){
        this.lf = lf;
    }

    @Override
    public Object format() throws IOException {
        return BCEL.encode(Stream.F2B(this.lf));
    }
}
