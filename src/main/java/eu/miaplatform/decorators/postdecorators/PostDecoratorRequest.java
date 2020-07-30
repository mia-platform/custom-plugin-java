package eu.miaplatform.decorators.postdecorators;

import eu.miaplatform.decorators.*;
import eu.miaplatform.service.environment.EnvConfiguration;
import lombok.*;

import java.io.Serializable;
import java.util.Map;

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

    public Serializable getOriginalRequestBody() {
        return this.request.getBody();
    }

    public DecoratorResponse getOriginalResponse() {
        return this.response;
    }

    public int getOriginalResponseStatusCode() {
        return this.response.getStatusCode();
    }

    public Map<String, String> getOriginalResponseHeaders() {
        return this.response.getHeaders();
    }

    public Serializable getOriginalResponseBody() {
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
