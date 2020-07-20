package eu.miaplatform.decorators.predecorators;

import eu.miaplatform.decorators.DecoratorRequest;

import java.util.Map;

public class PreDecoratorRequestProxy {
    public static class Builder {
        private final DecoratorRequest request;

        public Builder(DecoratorRequest originalRequest) {
            this.request = originalRequest;
        }

        public Builder setBody(Object body) {
            this.request.setBody(body);
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
