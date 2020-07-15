package eu.miaplatform.decorators;

import lombok.*;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class DecoratorResponse implements DecoratorChangingTarget {
    private Map<String, String> headers;
    private int statusCode;
    private DecoratorChangingTarget body;
}
