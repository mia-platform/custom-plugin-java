package eu.miaplatform.decorators.predecorators;

import eu.miaplatform.decorators.DecoratorRequest;
import lombok.*;

import java.util.Map;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PreDecoratorRequest {
    private String method;
    private String path;
    private Map<String, String> headers;
    private Map<String, String> query;
    private Object body;

    public PreDecoratorRequestProxy.Builder changeOriginalRequest() {
        return new PreDecoratorRequestProxy.Builder(
                DecoratorRequest.builder()
                        .method(this.getMethod())
                        .path(this.getPath())
                        .headers(this.getHeaders())
                        .query(this.getQuery())
                        .body(this.getBody())
                        .build());
    }

    public PreDecoratorRequest leaveOriginalRequestUnmodified() {
        return null;
    }

    protected DecoratorRequest getOriginalRequest() {
        return DecoratorRequest.builder()
                .method(this.method)
                .path(this.path)
                .headers(this.headers)
                .query(this.query)
                .body(this.body)
                .build();
    }

    public Object getOriginalRequestBody() {
        return this.getBody();
    }

    public Map<String, String> getOriginalRequestHeaders() {
        return this.getHeaders();
    }

    public String getOriginalRequestMethod() {
        return this.getMethod();
    }

    public String getOriginalRequestPath() {
        return this.getPath();
    }

    public DecoratorRequest getResponseBody() {
        return DecoratorRequest.builder()
                .method(this.method)
                .path(this.path)
                .headers(this.headers)
                .query(this.query)
                .body(this.body)
                .build();
    }
}