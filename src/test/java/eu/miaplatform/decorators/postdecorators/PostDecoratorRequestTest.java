package eu.miaplatform.decorators.postdecorators;

import eu.miaplatform.decorators.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class PostDecoratorRequestTest {
    @Test
    public void originalResponseUnmodified() {
        SampleBody sampleBody = SampleBody.builder().foo("foo").bar(42).baz(true).build();
        DecoratorRequest<SampleBody> originalRequest = DecoratorRequest.<SampleBody>builder().method("GET").path("/test").body(sampleBody).build();
        DecoratorResponse<SampleBody> originalResponse = DecoratorResponse.<SampleBody>builder().body(sampleBody).statusCode(200).build();

        PostDecoratorRequest<SampleBody, SampleBody> postDecoratorRequest = PostDecoratorRequest.<SampleBody, SampleBody>builder()
                .request(originalRequest)
                .response(originalResponse)
                .build();

        PostDecoratorRequest<SampleBody, SampleBody> updatedRequest = postDecoratorRequest.leaveOriginalResponseUnmodified();

        assertNull(updatedRequest);
    }

    @Test
    public void originalResponseGetsModified() {
        SampleBody originalBody = SampleBody.builder().foo("foo").bar(42).baz(true).build();
        SampleBody updatedBody = SampleBody.builder().foo("bar").bar(1).baz(false).build();

        DecoratorRequest<SampleBody> originalRequest = DecoratorRequest.<SampleBody>builder().method("GET").path("/test").body(originalBody).build();
        DecoratorResponse<SampleBody> originalResponse = DecoratorResponse.<SampleBody>builder().body(originalBody).statusCode(200).build();

        PostDecoratorRequest<SampleBody, SampleBody> postDecoratorRequest = PostDecoratorRequest.<SampleBody, SampleBody>builder()
                .request(originalRequest)
                .response(originalResponse)
                .build();

        PostDecoratorRequest<SampleBody, SampleBody> updatedRequest = postDecoratorRequest.changeOriginalResponse()
                .setStatusCode(204)
                .setBody(updatedBody)
                .build();

        assertNotEquals(postDecoratorRequest.getOriginalResponseBody(), updatedRequest.getResponse().getBody());
        assertEquals(postDecoratorRequest.getOriginalResponseBody(), originalBody);
        assertEquals(updatedRequest.getOriginalResponse().getBody(), updatedBody);
        assertEquals(updatedRequest.getOriginalResponse().getStatusCode(), 204);
    }
}
