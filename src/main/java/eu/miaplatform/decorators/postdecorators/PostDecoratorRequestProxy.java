package eu.miaplatform.decorators.postdecorators;

import eu.miaplatform.decorators.DecoratorRequest;
import eu.miaplatform.decorators.DecoratorResponse;

import java.io.Serializable;
import java.util.Map;

public class PostDecoratorRequestProxy {
    public static class Builder {
        private DecoratorRequest request;
        private DecoratorResponse response;

        public Builder(DecoratorRequest originalRequest, DecoratorResponse originalResponse) {
            this.request = originalRequest;
            this.response = originalResponse;
        }

        public Builder setBody(Serializable body) {
            this.response.setBody(body);
            return this;
        }

        public Builder setStatusCode(int statusCode) {
            this.response.setStatusCode(statusCode);
            return this;
        }

        public Builder setHeaders(Map<String, String> headers) {
            this.response.setHeaders(headers);
            return this;
        }

        public PostDecoratorRequest build() {
            return PostDecoratorRequest.builder().request(request).response(response).build();
        }
    }

    private PostDecoratorRequestProxy() {

    }
}
