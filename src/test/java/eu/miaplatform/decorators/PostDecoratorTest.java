package eu.miaplatform.decorators;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class PostDecoratorTest {
    @Test
    public void originalRequestUnmodified() {
        Request originalRequest = Request.builder().method("GET").path("/test").body("{\"foo\":\"bar\"}").build();
        Response originalResponse = Response.builder().body("{\"bar\":\"baz\"})").statusCode(200).build();

        PostDecoratorRequest postDecoratorRequest = PostDecoratorRequest.builder()
                .request(originalRequest)
                .response(originalResponse)
                .build();

        PostDecoratorRequest updatedRequest = postDecoratorRequest.leaveOriginalRequestUnmodified();

        assertEquals(postDecoratorRequest.getOriginalRequestBody(), updatedRequest.getOriginalRequestBody());
        assertEquals(postDecoratorRequest.getOriginalResponseBody(), updatedRequest.getOriginalResponseBody());
        assertEquals(postDecoratorRequest.getOriginalResponseStatusCode(), updatedRequest.getOriginalResponseStatusCode());
    }

    @Test
    public void originalRequestGetsModified() {
        Request originalRequest = Request.builder().method("GET").path("/test").body("{\"foo\":\"bar\"}").build();
        Response originalResponse = Response.builder().body("{\"bar\":\"baz\"}").statusCode(200).build();

        PostDecoratorRequest postDecoratorRequest = PostDecoratorRequest.builder()
                .request(originalRequest)
                .response(originalResponse)
                .build();

        PostDecoratorRequest updatedRequest = postDecoratorRequest.changeOriginalRequest()
                .setStatusCode(204)
                .setBody("{\"a\":\"42\"}")
                .build();

        assertNotEquals(postDecoratorRequest.getOriginalResponseBody(), updatedRequest.getOriginalResponse().getBody());
        assertEquals(postDecoratorRequest.getOriginalResponseBody(), "{\"bar\":\"baz\"}");
        assertEquals(updatedRequest.getOriginalResponse().getBody(), "{\"a\":\"42\"}");
        assertEquals(updatedRequest.getOriginalResponse().getStatusCode(), 204);
    }
}
