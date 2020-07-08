package eu.miaplatform.customplugin;

import java.util.Map;

public class DecoratorRequest {
    private String method;
    private String path;
    private CustomPluginHeadersPropagator headers;
    private byte[] body;
    private Map<String, String[]> modifiableQueryParameters;

    public DecoratorRequest(String method, String path, CustomPluginHeadersPropagator headers, byte[] body, Map<String, String[]> modifiableQueryParameters) {
        this.method = method;
        this.path = path;
        this.headers = headers;
        this.body = body;
        this.modifiableQueryParameters = modifiableQueryParameters;
    }
}
