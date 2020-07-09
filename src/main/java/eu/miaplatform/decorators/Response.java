package eu.miaplatform.decorators;

import lombok.Data;

import java.util.Map;

@Data
public class Response {
    private Map<String, String> headers;
    private int statusCode;
    private String body;
}
