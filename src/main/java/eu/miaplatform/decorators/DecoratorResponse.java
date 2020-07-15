package eu.miaplatform.decorators;

import lombok.*;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class DecoratorResponse {
    private Map<String, String> headers;
    private int statusCode;
    private Object body;
}
