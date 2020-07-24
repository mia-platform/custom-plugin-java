package eu.miaplatform.decorators;

import lombok.*;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DecoratorRequest<T> {
    private String method;
    private String path;
    private Map<String, String> headers;
    private Map<String, String> query;
    private T body;
}
