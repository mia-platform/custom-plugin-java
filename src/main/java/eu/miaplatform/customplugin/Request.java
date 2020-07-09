package eu.miaplatform.customplugin;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class Request {
    private String method;
    private String path;
    private Map<String, String> headers;
    private String query;
    private String body;
}
