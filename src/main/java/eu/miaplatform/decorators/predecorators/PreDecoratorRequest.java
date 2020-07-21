package eu.miaplatform.decorators.predecorators;

import eu.miaplatform.decorators.DecoratorRequest;
import eu.miaplatform.service.EnvConfiguration;
import eu.miaplatform.service.EnvConfigurationException;
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

    public DecoratorRequest getRequest() {
        return DecoratorRequest.builder()
                .method(this.method)
                .path(this.path)
                .headers(this.headers)
                .query(this.query)
                .body(this.body)
                .build();
    }

    public String getOriginalRequestPath() {
        return this.getPath();
    }

    public String getOriginalRequestMethod() {
        return this.getMethod();
    }

    public Map<String, String> getOriginalRequestHeaders() {
        return this.getHeaders();
    }

    public Map<String, String> getOriginalRequestQuery() {
        return this.getQuery();
    }

    public Object getOriginalRequestBody() {
        return this.getBody();
    }

    public String getUserId() {
        try  {
            return this.headers.get(EnvConfiguration.get("USERID_HEADER_KEY"));
        } catch(EnvConfigurationException ex) {
            return null;
        }
    }

    public String getGroups() {
        try  {
            return this.headers.get(EnvConfiguration.get("GROUPS_HEADER_KEY"));
        } catch(EnvConfigurationException ex) {
            return null;
        }
    }
    public String getClientType() {
        try  {
            return this.headers.get(EnvConfiguration.get("GROUPS_HEADER_KEY"));
        } catch(EnvConfigurationException ex) {
            return null;
        }
    }

    public String isFromBackOffice() {
        try  {
            return this.headers.get(EnvConfiguration.get("BACKOFFICE_HEADER_KEY"));
        } catch(EnvConfigurationException ex) {
            return null;
        }
    }
}
