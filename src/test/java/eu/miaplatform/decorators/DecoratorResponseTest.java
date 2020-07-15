package eu.miaplatform.decorators;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DecoratorResponseTest {
    @Test
    public void leaveOriginalRequestUnmodified() {
        Request originalRequest = Request.builder().method("GET").path("/test").body("{\"foo\":\"bar\"}").build();
        PreDecoratorRequest preDecoratorRequest = PreDecoratorRequest.builder().request(originalRequest).build();
        Response response = DecoratorResponseFactory.makeResponse(preDecoratorRequest.leaveOriginalRequestUnmodified());
        assertEquals(response.getStatusCode(), 204);
        assertNull(response.getHeaders());
        assertNull(response.getBody());
    }
}
