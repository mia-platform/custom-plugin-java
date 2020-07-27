package eu.miaplatform.decorators;

import eu.miaplatform.decorators.postdecorators.PostDecoratorRequest;
import eu.miaplatform.decorators.predecorators.PreDecoratorRequest;
import org.junit.Test;

import static org.junit.Assert.*;

public class DecoratorResponseFactoryTest {
    @Test
    public void leaveOriginalRequestUnmodified() {
        SampleBody sampleBody = SampleBody.builder().foo("foo").bar(42).baz(true).build();
        PreDecoratorRequest<SampleBody> preDecoratorRequest = PreDecoratorRequest.<SampleBody>builder().method("GET").path("/test").body(sampleBody).build();

        DecoratorResponse<SampleBody> response = DecoratorResponseFactory.makePreDecoratorResponse(preDecoratorRequest.leaveOriginalRequestUnmodified());

        assertEquals(response.getStatusCode(), 204);
        assertNotNull(response.getHeaders().get("Content-Type"));
        assertEquals(response.getHeaders().get("Content-Type"), "application/json; charset=utf-8");
        assertNull(response.getBody());
    }

    @Test
    public void changeOriginalRequest() {
        SampleBody sampleBody = SampleBody.builder().foo("foo").bar(42).baz(true).build();
        PreDecoratorRequest<SampleBody> preDecoratorRequest = PreDecoratorRequest.<SampleBody>builder().method("GET").path("/test").body(sampleBody).build();

        DecoratorResponse<SampleBody> response = DecoratorResponseFactory.makePreDecoratorResponse(preDecoratorRequest);

        assertEquals(response.getStatusCode(), 200);
        assertNotNull(response.getHeaders().get("Content-Type"));
        assertEquals(response.getHeaders().get("Content-Type"), "application/json; charset=utf-8");
    }

    @Test
    public void leaveOriginalResponseUnmodified() {
        SampleBody sampleBody = SampleBody.builder().foo("foo").bar(42).baz(true).build();
        DecoratorRequest<SampleBody> originalRequest = DecoratorRequest.<SampleBody>builder().method("GET").path("/test").body(sampleBody).build();
        DecoratorResponse<SampleBody> originalResponse = DecoratorResponse.<SampleBody>builder().body(sampleBody).statusCode(200).build();

        PostDecoratorRequest<SampleBody, SampleBody> postDecoratorRequest = PostDecoratorRequest.<SampleBody, SampleBody>builder()
                .request(originalRequest)
                .response(originalResponse)
                .build();
        DecoratorResponse<SampleBody> response = DecoratorResponseFactory.makePostDecoratorResponse(postDecoratorRequest.leaveOriginalResponseUnmodified());

        assertEquals(response.getStatusCode(), 204);
        assertNotNull(response.getHeaders().get("Content-Type"));
        assertEquals(response.getHeaders().get("Content-Type"), "application/json; charset=utf-8");
        assertNull(response.getBody());
    }

    @Test
    public void changeOriginalResponse() {
        SampleBody sampleBody = SampleBody.builder().foo("foo").bar(42).baz(true).build();
        DecoratorRequest<SampleBody> originalRequest = DecoratorRequest.<SampleBody>builder().method("GET").path("/test").body(sampleBody).build();
        DecoratorResponse<SampleBody> originalResponse = DecoratorResponse.<SampleBody>builder().body(sampleBody).statusCode(200).build();

        PostDecoratorRequest<SampleBody, SampleBody> postDecoratorRequest = PostDecoratorRequest.<SampleBody, SampleBody>builder()
                .request(originalRequest)
                .response(originalResponse)
                .build();
        DecoratorResponse<SampleBody> response = DecoratorResponseFactory.makePostDecoratorResponse(postDecoratorRequest);

        assertEquals(response.getStatusCode(), 200);
        assertNotNull(response.getHeaders().get("Content-Type"));
        assertEquals(response.getHeaders().get("Content-Type"), "application/json; charset=utf-8");
    }

    @Test
    public void abortChain() {
        DecoratorResponse<SampleBody> response = DecoratorResponseFactory.abortChain(404);

        assertEquals(response.getStatusCode(), 418);
        assertNotNull(response.getHeaders().get("Content-Type"));
        assertEquals(response.getHeaders().get("Content-Type"), "application/json; charset=utf-8");
    }
}
