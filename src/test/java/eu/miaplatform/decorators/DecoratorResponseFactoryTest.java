package eu.miaplatform.decorators;

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
}
