package eu.miaplatform.decorators.predecorators;

import eu.miaplatform.decorators.DecoratorRequest;
import eu.miaplatform.decorators.DecoratorResponse;
import org.junit.Test;

import static org.junit.Assert.*;

public class PreDecoratorResponseFactoryTest {
    @Test
    public void leaveOriginalRequestUnmodified() {
        PreDecoratorRequest preDecoratorRequest = PreDecoratorRequest.builder().method("GET").path("/test").body("{\"foo\":\"bar\"}").build();
        DecoratorResponse response = PreDecoratorResponseFactory.makeResponse(preDecoratorRequest.leaveOriginalRequestUnmodified());
        assertEquals(response.getStatusCode(), 204);
        assertNotNull(response.getHeaders().get("Content-Type"));
        assertEquals(response.getHeaders().get("Content-Type"), "application/json; charset=utf-8");
        assertNull(response.getBody());
    }
}
