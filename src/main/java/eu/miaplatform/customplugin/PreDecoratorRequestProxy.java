package eu.miaplatform.customplugin;

import java.util.Map;

public class PreDecoratorRequestProxy {
    private final Request request;

    public PreDecoratorRequestProxy(Request originalRequest) {
        this.request = originalRequest;
    }

    public PreDecoratorRequest setBody(String body) {
        request.setBody(body);
        return PreDecoratorRequest.builder().request(request).build();
    }

    public PreDecoratorRequest setHeaders(Map<String,String> headers) {
        request.setHeaders(headers);
        return PreDecoratorRequest.builder().request(request).build();
    }

    public PreDecoratorRequest setQuery(String query) {
        request.setQuery(query);
        return PreDecoratorRequest.builder().request(request).build();
    }
}
