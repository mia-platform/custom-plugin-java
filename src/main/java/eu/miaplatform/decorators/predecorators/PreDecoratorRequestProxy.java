package eu.miaplatform.decorators.predecorators;

import eu.miaplatform.decorators.DecoratorRequest;

import java.io.Serializable;
import java.util.Map;

public class PreDecoratorRequestProxy {
    public static class Builder {
        private final DecoratorRequest request;

        public Builder(DecoratorRequest originalRequest) {
            this.request = originalRequest;
        }

        public Builder setMethod(String method) {
            this.request.setMethod(method);
            return this;
        }
        public Builder setPath(String path) {
            this.request.setPath(path);
            return this;
        }
        public Builder setHeaders(Map<String, String> headers) {
            this.request.setHeaders(headers);
            return this;
        }

        public Builder setQuery(Map<String, String> query) {
            this.request.setQuery(query);
            return this;
        }

        public Builder setBody(Serializable body) {
            this.request.setBody(body);
            return this;
        }

        public PreDecoratorRequest build() {
            return PreDecoratorRequest.builder()
                    .method(request.getMethod())
                    .path(request.getPath())
                    .headers(request.getHeaders())
                    .query(request.getQuery())
                    .body(request.getBody())
                    .build();
        }
    }

    private PreDecoratorRequestProxy() {

    }
}
