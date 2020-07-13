package eu.miaplatform.decorators;


import java.util.Map;

public class PostDecoratorRequestProxy {
    private Request request;
    private Response response;

    public PostDecoratorRequestProxy(Request originalRequest, Response originalResponse) {
        this.request = originalRequest;
        this.response = originalResponse;
    }

    public PostDecoratorRequest setBody(String body) {
        this.response.setBody(body);
        return PostDecoratorRequest.builder().request(request).response(response).build();
    }

    public PostDecoratorRequest setStatusCode(int statusCode) {
        this.response.setStatusCode(statusCode);
        return PostDecoratorRequest.builder().request(request).response(response).build();
    }

    public PostDecoratorRequest setHeaders(Map<String,String> headers) {
        this.response.setHeaders(headers);
        return PostDecoratorRequest.builder().request(request).response(response).build();
    }
}
