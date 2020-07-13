package eu.miaplatform.decorators;

import java.util.Map;

public class PostDecoratorRequestProxy {
    public static class Builder {
        private Request request;
        private Response response;

        public Builder(Request originalRequest, Response originalResponse) {
            this.request = originalRequest;
            this.response = originalResponse;
        }

        public Builder setBody(String body) {
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
