package eu.miaplatform.decorators;

import eu.miaplatform.decorators.postdecorators.PostDecoratorRequest;
import eu.miaplatform.decorators.predecorators.PreDecoratorRequest;
import org.junit.Test;

import static org.junit.Assert.*;

public class DecoratorResponseFactoryTest {
    @Test
    public void leaveOriginalRequestUnmodified() {
        PreDecoratorRequest preDecoratorRequest = PreDecoratorRequest.builder().method("GET").path("/test").body("{\"foo\":\"bar\"}").build();

        DecoratorResponse response = DecoratorResponseFactory.makePreDecoratorResponse(preDecoratorRequest.leaveOriginalRequestUnmodified());

        assertEquals(response.getStatusCode(), 204);
        assertNotNull(response.getHeaders().get("Content-Type"));
        assertEquals(response.getHeaders().get("Content-Type"), "application/json; charset=utf-8");
        assertNull(response.getBody());
    }

    @Test
    public void changeOriginalRequest() {
        PreDecoratorRequest preDecoratorRequest = PreDecoratorRequest.builder().method("GET").path("/test").body("{\"foo\":\"bar\"}").build();

        DecoratorResponse response = DecoratorResponseFactory.makePreDecoratorResponse(preDecoratorRequest);

        assertEquals(response.getStatusCode(), 200);
        assertNotNull(response.getHeaders().get("Content-Type"));
        assertEquals(response.getHeaders().get("Content-Type"), "application/json; charset=utf-8");
    }

    @Test
    public void leaveOriginalResponseUnmodified() {
        DecoratorRequest originalRequest = DecoratorRequest.builder().method("GET").path("/test").body("{\"foo\":\"bar\"}").build();
        DecoratorResponse originalResponse = DecoratorResponse.builder().body("{\"bar\":\"baz\"}").statusCode(200).build();

        PostDecoratorRequest postDecoratorRequest = PostDecoratorRequest.builder()
                .request(originalRequest)
                .response(originalResponse)
                .build();
        DecoratorResponse response = DecoratorResponseFactory.makePostDecoratorResponse(postDecoratorRequest.leaveOriginalResponseUnmodified());

        assertEquals(response.getStatusCode(), 204);
        assertNotNull(response.getHeaders().get("Content-Type"));
        assertEquals(response.getHeaders().get("Content-Type"), "application/json; charset=utf-8");
        assertNull(response.getBody());
    }

    @Test
    public void changeOriginalResponse() {
        DecoratorRequest originalRequest = DecoratorRequest.builder().method("GET").path("/test").body("{\"foo\":\"bar\"}").build();
        DecoratorResponse originalResponse = DecoratorResponse.builder().body("{\"bar\":\"baz\"}").statusCode(200).build();

        PostDecoratorRequest postDecoratorRequest = PostDecoratorRequest.builder()
                .request(originalRequest)
                .response(originalResponse)
                .build();
        DecoratorResponse response = DecoratorResponseFactory.makePostDecoratorResponse(postDecoratorRequest);

        assertEquals(response.getStatusCode(), 200);
        assertNotNull(response.getHeaders().get("Content-Type"));
        assertEquals(response.getHeaders().get("Content-Type"), "application/json; charset=utf-8");
    }

    @Test
    public void abortChain() {
        DecoratorResponse response = DecoratorResponseFactory.abortChain(404);

        assertEquals(response.getStatusCode(), 418);
        assertNotNull(response.getHeaders().get("Content-Type"));
        assertEquals(response.getHeaders().get("Content-Type"), "application/json; charset=utf-8");
    }
}
