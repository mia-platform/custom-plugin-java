package eu.miaplatform.decorators.postdecorators;

import eu.miaplatform.decorators.*;
import eu.miaplatform.service.environment.EnvConfiguration;
import lombok.*;

import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostDecoratorRequest<T, U> {
    private DecoratorRequest<T> request;
    private DecoratorResponse<U> response;

    public PostDecoratorRequestProxy.Builder<T, U> changeOriginalResponse() {
        return new PostDecoratorRequestProxy.Builder<T, U>(
                DecoratorRequest.<T>builder()
                        .method(this.request.getMethod())
                        .path(this.request.getPath())
                        .headers(this.request.getHeaders())
                        .query(this.request.getQuery())
                        .body(this.request.getBody())
                        .build(),
                DecoratorResponse.<U>builder()
                        .statusCode(this.response.getStatusCode())
                        .headers(this.response.getHeaders())
                        .body(this.response.getBody())
                        .build());
    }

    public PostDecoratorRequest<T, U> leaveOriginalResponseUnmodified() {
        return null;
    }

    public String getOriginalRequestMethod() {
        return this.request.getMethod();
    }

    public String getOriginalRequestPath() {
        return this.request.getPath();
    }

    public Map<String, String> getOriginalRequestHeaders() {
        return this.request.getHeaders();
    }

    public Map<String, String> getOriginalRequestQuery() {
        return this.request.getQuery();
    }

    public T getOriginalRequestBody() {
        return this.request.getBody();
    }

    public DecoratorResponse<U> getOriginalResponse() {
        return this.response;
    }

    public int getOriginalResponseStatusCode() {
        return this.response.getStatusCode();
    }

    public Map<String, String> getOriginalResponseHeaders() {
        return this.response.getHeaders();
    }

    public U getOriginalResponseBody() {
        return this.response.getBody();
    }

    public String getUserId() {
        return this.request.getHeaders().get(EnvConfiguration.getInstance().get("USERID_HEADER_KEY"));
    }

    public String getGroups() {
        return this.request.getHeaders().get(EnvConfiguration.getInstance().get("GROUPS_HEADER_KEY"));
    }

    public String getClientType() {
        return this.request.getHeaders().get(EnvConfiguration.getInstance().get("GROUPS_HEADER_KEY"));
    }

    public String isFromBackOffice() {
        return this.request.getHeaders().get(EnvConfiguration.getInstance().get("BACKOFFICE_HEADER_KEY"));
    }

}
