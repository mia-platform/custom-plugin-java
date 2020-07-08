package eu.miaplatform.service;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
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
}
