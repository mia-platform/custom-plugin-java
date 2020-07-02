package eu.miaplatform.service;

import com.google.gson.JsonObject;

public class InitServiceOptions {
    private int port;
    private Protocol protocol;
    private JsonObject header;
    private String prefix;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public JsonObject getHeader() {
        return header;
    }

    public void setHeader(JsonObject header) {
        this.header = header;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public InitServiceOptions(int port, Protocol protocol, JsonObject header, String prefix) {
        this.port = port;
        this.protocol = protocol;
        this.header = header;
        this.prefix = prefix;
    }
}
