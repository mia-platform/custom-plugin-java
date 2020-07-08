package eu.miaplatform.service;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InitServiceOptions {
    @Builder.Default
    private int port = 3000;
    @Builder.Default
    private Protocol protocol = Protocol.HTTP;
    @Builder.Default
    private Map<String, String> headers = new HashMap<>();
    private String prefix;
}
