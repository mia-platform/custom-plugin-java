package eu.miaplatform.decorators.predecorators;

import eu.miaplatform.decorators.DecoratorRequest;
import lombok.*;

import java.util.Map;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PreDecoratorRequest {
    private DecoratorRequest request;

    public PreDecoratorRequestProxy.Builder changeOriginalRequest() {
        return new PreDecoratorRequestProxy.Builder(
                DecoratorRequest.builder()
                        .method(this.request.getMethod())
                        .path(this.request.getPath())
                        .headers(this.request.getHeaders())
                        .query(this.request.getQuery())
                        .body(this.request.getBody())
                        .build());
    }

    public PreDecoratorRequest leaveOriginalRequestUnmodified() {
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

    public DecoratorRequest getResponseBody() {
        return this.request;
    }

    public void setQuery(String path) {
        this.request.setPath("foo");
    }
}