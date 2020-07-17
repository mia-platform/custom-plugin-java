package eu.miaplatform.decorators.postdecorators;

import eu.miaplatform.decorators.*;
import lombok.*;

import java.util.Map;

import static eu.miaplatform.decorators.constants.DecoratorConstants.ABORT_CHAIN_STATUS_CODE;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostDecoratorRequest {
    private DecoratorRequest request;
    private DecoratorResponse response;

    public PostDecoratorRequestProxy.Builder changeOriginalResponse() {
        return new PostDecoratorRequestProxy.Builder(
                DecoratorRequest.builder()
                        .method(this.request.getMethod())
                        .path(this.request.getPath())
                        .headers(this.request.getHeaders())
                        .query(this.request.getQuery())
                        .body(this.request.getBody  ())
                        .build(),
                DecoratorResponse.builder()
                        .statusCode(this.response.getStatusCode())
                        .headers(this.response.getHeaders())
                        .body(this.response.getBody())
                        .build());
    }

    public PostDecoratorRequest leaveOriginalResponseUnmodified() {
        return null;
    }

    protected DecoratorRequest getOriginalRequest() {
        return this.request;
    }

    public Object getOriginalRequestBody() {
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
        return this.response.getStatusCode();
    }

    public DecoratorResponse getResponseBody() {
        return this.response;
    }
}
