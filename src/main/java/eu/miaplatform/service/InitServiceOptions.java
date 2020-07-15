package eu.miaplatform.service;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.Map;

@Data
@SuperBuilder
public class InitServiceOptions {
    @Builder.Default
    private int port = 3000;
    @Builder.Default
    private Protocol protocol = Protocol.HTTP;
    @Builder.Default
    private Map<String, String> headers = new HashMap<>();
    private String prefix;

    public InitServiceOptions(int port, Protocol protocol, Map<String, String> headers, String prefix) {
        this.port = port;
        this.protocol = protocol;
        this.headers = headers;
        this.prefix = prefix;
    }

    public InitServiceOptions() {}
}
