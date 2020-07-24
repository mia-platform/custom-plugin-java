package eu.miaplatform.decorators.predecorators;

import eu.miaplatform.decorators.DecoratorRequest;

import java.util.Map;

public class PreDecoratorRequestProxy {
    public static class Builder<T> {
        private final DecoratorRequest<T> request;

        public Builder(DecoratorRequest<T> originalRequest) {
            this.request = originalRequest;
        }

        public Builder<T> setMethod(String method) {
            this.request.setMethod(method);
            return this;
        }
        public Builder<T> setPath(String path) {
            this.request.setPath(path);
            return this;
        }
        public Builder<T> setHeaders(Map<String, String> headers) {
            this.request.setHeaders(headers);
            return this;
        }

        public Builder<T> setQuery(Map<String, String> query) {
            this.request.setQuery(query);
            return this;
        }

        public Builder<T> setBody(T body) {
            this.request.setBody(body);
            return this;
        }

        public PreDecoratorRequest<T> build() {
            return PreDecoratorRequest.<T>builder()
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
