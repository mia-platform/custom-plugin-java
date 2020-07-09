package eu.miaplatform.decorators;

import lombok.Builder;

import java.util.Map;

@Builder
public class PreDecoratorRequest {
    private final Request request;

    public PreDecoratorRequestProxy changeOriginalRequest() {
        return new PreDecoratorRequestProxy(
                Request.builder()
                        .method(this.request.getMethod())
                        .path(this.request.getPath())
                        .headers(this.request.getHeaders())
                        .query(this.request.getQuery())
                        .body(this.request.getBody())
                        .build());
    }

    protected Request getRequest() {
        return this.request;
    }

    public PreDecoratorRequest leaveOriginalRequestUnmodified() {
        return this;
    }

    public PreDecoratorRequest abortChain() {
        return this;
    }

    public String getOriginalRequestBody() {
        return this.request.getBody();
    }

    public Map<String, String> getOriginalRequestHeaders() {
        return this.request.getHeaders();
    }

    public String getOriginalRequestMethod() {
        return this.request.getMethod();
    }

    public String getOriginalRequestPath() {
        return this.request.getPath();
    }
}
