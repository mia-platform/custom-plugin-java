package eu.miaplatform.service;

import java.util.HashMap;
import java.util.Map;

public class InitServiceOptions {
    private int port;
    private Protocol protocol;
    private Map<String, String> headers;
    private String prefix;

    public InitServiceOptions() {
        this.port = 3000;
        this.protocol = Protocol.HTTP;
        this.headers = new HashMap<>();
        this.prefix = "";
    }

    public InitServiceOptions(int port, Protocol protocol, Map<String, String> headers, String prefix) {
        this.port = port;
        this.protocol = protocol;
        this.headers = headers;
        this.prefix = prefix;
    }

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

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
