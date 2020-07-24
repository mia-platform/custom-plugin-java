package eu.miaplatform.decorators.postdecorators;

import eu.miaplatform.decorators.DecoratorRequest;
import eu.miaplatform.decorators.DecoratorResponse;

import java.util.Map;

public class PostDecoratorRequestProxy {
    public static class Builder<T, U> {
        private DecoratorRequest<T> request;
        private DecoratorResponse<U> response;

        public Builder(DecoratorRequest<T> originalRequest, DecoratorResponse<U> originalResponse) {
            this.request = originalRequest;
            this.response = originalResponse;
        }

        public Builder<T, U> setBody(U body) {
            this.response.setBody(body);
            return this;
        }

        public Builder<T, U> setStatusCode(int statusCode) {
            this.response.setStatusCode(statusCode);
            return this;
        }

        public Builder<T, U> setHeaders(Map<String, String> headers) {
            this.response.setHeaders(headers);
            return this;
        }

        public PostDecoratorRequest<T, U> build() {
            return PostDecoratorRequest.<T, U>builder().request(request).response(response).build();
        }
    }

    private PostDecoratorRequestProxy() {

    }
}
