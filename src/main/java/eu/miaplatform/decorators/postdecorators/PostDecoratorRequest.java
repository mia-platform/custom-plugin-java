package eu.miaplatform.decorators.postdecorators;

import eu.miaplatform.decorators.DecoratorRequest;
import eu.miaplatform.decorators.DecoratorResponse;
import lombok.Builder;

import java.util.Map;

@Builder
public class PostDecoratorRequest {
    private DecoratorRequest request;
    private DecoratorResponse response;

    public PostDecoratorRequestProxy.Builder changeOriginalRequest() {
        return new PostDecoratorRequestProxy.Builder(
                DecoratorRequest.builder()
                        .method(this.request.getMethod())
                        .path(this.request.getPath())
                        .headers(this.request.getHeaders())
                        .query(this.request.getQuery())
                        .body(this.request.getBody())
                        .build(),
                DecoratorResponse.builder()
                        .statusCode(this.response.getStatusCode())
                        .headers(this.response.getHeaders())
                        .body(this.response.getBody())
                        .build());
    }

    protected DecoratorRequest getOriginalRequest() {
        return this.request;
    }

    public PostDecoratorRequest leaveOriginalRequestUnmodified() {
        return this;
    }

    public PostDecoratorRequest abortChain() {
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

    public DecoratorResponse getOriginalResponse() {
        return this.response;
    }

    public Map<String, String> getOriginalResponseHeaders() {
        return this.response.getHeaders();
    }

    public Object getOriginalResponseBody() {
        return this.response.getBody();
    }

    public int getOriginalResponseStatusCode() {
        return  this.response.getStatusCode();
    }

    public DecoratorResponse getResponseBody() {
        return this.response;
    }
}
