package eu.miaplatform.decorators.postdecorators;

import eu.miaplatform.decorators.*;
import eu.miaplatform.service.EnvConfiguration;
import eu.miaplatform.service.EnvConfigurationException;
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

    public Object getOriginalRequestBody() {
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

    public Object getOriginalResponseBody() {
        return this.response.getBody();
    }

    public String getUserId() {
        try  {
            return this.request.getHeaders().get(EnvConfiguration.get("USERID_HEADER_KEY"));
        } catch(EnvConfigurationException ex) {
            return null;
        }
    }

    public String getGroups() {
        try  {
            return this.request.getHeaders().get(EnvConfiguration.get("GROUPS_HEADER_KEY"));
        } catch(EnvConfigurationException ex) {
            return null;
        }
    }
    public String getClientType() {
        try  {
            return this.request.getHeaders().get(EnvConfiguration.get("GROUPS_HEADER_KEY"));
        } catch(EnvConfigurationException ex) {
            return null;
        }
    }

    public String isFromBackOffice() {
        try  {
            return this.request.getHeaders().get(EnvConfiguration.get("BACKOFFICE_HEADER_KEY"));
        } catch(EnvConfigurationException ex) {
            return null;
        }
    }

}
