package eu.miaplatform.decorators;

import lombok.Builder;

import java.util.Map;

@Builder
public class PostDecoratorRequest {
    private Request request;
    private Response response;

    public PostDecoratorRequestProxy changeOriginalRequest() {
        return new PostDecoratorRequestProxy(
                Request.builder()
                        .method(this.request.getMethod())
                        .path(this.request.getPath())
                        .headers(this.request.getHeaders())
                        .query(this.request.getQuery())
                        .body(this.request.getBody())
                        .build(),
                Response.builder()
                        .statusCode(this.response.getStatusCode())
                        .headers(this.response.getHeaders())
                        .body(this.response.getBody())
                        .build());
    }

    protected Request getOriginalRequest() {
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

    public Response getOriginalResponse() {
        return this.response;
    }

    public Map<String, String> getOriginalResponseHeaders() {
        return this.response.getHeaders();
    }

    public String getOriginalResponseBody() {
        return this.response.getBody();
    }

    public int getOriginalResponseStatusCode() {
        return  this.response.getStatusCode();
    }
}
