package eu.miaplatform.decorators;

import lombok.*;

import java.io.Serializable;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DecoratorResponse implements Serializable {
    private int statusCode;
    private Map<String, String> headers;
    private Serializable body;
}
