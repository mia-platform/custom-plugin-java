package eu.miaplatform.customplugin;

import lombok.Data;

import java.util.Map;

@Data
public class Response {
    private Map<String, String> headers;
    private int statusCode;
    private String body;
}
