package eu.miaplatform.decorators;

import eu.miaplatform.decorators.predecorators.PreDecoratorRequest;
import eu.miaplatform.decorators.predecorators.PreDecoratorResponseFactory;
import org.junit.Test;

import static org.junit.Assert.*;

public class DecoratorResponseTest {
    @Test
    public void leaveOriginalRequestUnmodified() {
        DecoratorRequest originalRequest = DecoratorRequest.builder().method("GET").path("/test").body("{\"foo\":\"bar\"}").build();
        PreDecoratorRequest preDecoratorRequest = PreDecoratorRequest.builder().request(originalRequest).build();
        DecoratorResponse response = PreDecoratorResponseFactory.makeResponse(preDecoratorRequest.leaveOriginalRequestUnmodified());
        assertEquals(response.getStatusCode(), 204);
        assertNotNull(response.getHeaders().get("Content-Type"));
        assertEquals(response.getHeaders().get("Content-Type"), "application/json; charset=utf-8");
        assertNull(response.getBody());
    }
}
