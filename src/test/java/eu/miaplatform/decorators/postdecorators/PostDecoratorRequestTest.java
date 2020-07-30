package eu.miaplatform.decorators.postdecorators;

import eu.miaplatform.decorators.DecoratorRequest;
import eu.miaplatform.decorators.DecoratorResponse;
import org.junit.Test;

import static org.junit.Assert.*;

public class PostDecoratorRequestTest {
    @Test
    public void originalResponseUnmodified() {
        DecoratorRequest originalRequest = DecoratorRequest.builder().method("GET").path("/test").body("{\"foo\":\"bar\"}").build();
        DecoratorResponse originalResponse = DecoratorResponse.builder().body("{\"bar\":\"baz\"})").statusCode(200).build();

        PostDecoratorRequest postDecoratorRequest = PostDecoratorRequest.builder()
                .request(originalRequest)
                .response(originalResponse)
                .build();

        PostDecoratorRequest updatedRequest = postDecoratorRequest.leaveOriginalResponseUnmodified();

        assertNull(updatedRequest);
    }

    @Test
    public void originalResponseGetsModified() {
        DecoratorRequest originalRequest = DecoratorRequest.builder().method("GET").path("/test").body("{\"foo\":\"bar\"}").build();
        DecoratorResponse originalResponse = DecoratorResponse.builder().body("{\"bar\":\"baz\"}").statusCode(200).build();

        PostDecoratorRequest postDecoratorRequest = PostDecoratorRequest.builder()
                .request(originalRequest)
                .response(originalResponse)
                .build();

        PostDecoratorRequest updatedRequest = postDecoratorRequest.changeOriginalResponse()
                .setStatusCode(204)
                .setBody("{\"a\":\"42\"}")
                .build();

        assertNotEquals(postDecoratorRequest.getOriginalResponseBody(), updatedRequest.getOriginalResponse().getBody());
        assertEquals(postDecoratorRequest.getOriginalResponseBody(), "{\"bar\":\"baz\"}");
        assertEquals(updatedRequest.getOriginalResponse().getBody(), "{\"a\":\"42\"}");
        assertEquals(updatedRequest.getOriginalResponse().getStatusCode(), 204);
    }
}
