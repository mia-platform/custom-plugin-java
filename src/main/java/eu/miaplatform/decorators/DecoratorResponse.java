package eu.miaplatform.decorators;

import lombok.*;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DecoratorResponse<U> {
    private int statusCode;
    private Map<String, String> headers;
    private U body;
}
