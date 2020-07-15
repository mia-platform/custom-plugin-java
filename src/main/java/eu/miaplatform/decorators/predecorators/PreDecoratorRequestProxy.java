package eu.miaplatform.decorators.predecorators;

import eu.miaplatform.decorators.DecoratorRequest;

import java.util.Map;

public class PreDecoratorRequestProxy {
    public static class Builder {
        private final DecoratorRequest request;

        public Builder(DecoratorRequest originalRequest) {
            this.request = originalRequest;
        }

        public Builder setBody(String body) {
            this.request.setBody(body);
            return this;
        }

        public Builder setHeaders(Map<String, String> headers) {
            this.request.setHeaders(headers);
            return this;
        }

        public Builder setQuery(String query) {
            this.request.setQuery(query);
            return this;
        }

        public PreDecoratorRequest build() {
            return PreDecoratorRequest.builder().request(request).build();
        }
    }

    private PreDecoratorRequestProxy() {

    }
}
