package eu.miaplatform.decorators;

import java.util.Map;

public class PreDecoratorRequestProxy {
    private final Request request;

    public PreDecoratorRequestProxy(Request originalRequest) {
        this.request = originalRequest;
    }

    public PreDecoratorRequest setBody(String body) {
        this.request.setBody(body);
        return PreDecoratorRequest.builder().request(this.request).build();
    }

    public PreDecoratorRequest setHeaders(Map<String,String> headers) {
        this.request.setHeaders(headers);
        return PreDecoratorRequest.builder().request(this.request).build();
    }

    public PreDecoratorRequest setQuery(String query) {
        this.request.setQuery(query);
        return PreDecoratorRequest.builder().request(this.request).build();
    }
}
