package eu.miaplatform.decorators.predecorators;

import eu.miaplatform.decorators.DecoratorRequest;
import eu.miaplatform.decorators.DecoratorResponse;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PreDecoratorResponseFactoryTest {
    @Test
    public void leaveOriginalRequestUnmodified() {
        DecoratorRequest originalRequest = DecoratorRequest.builder().method("GET").path("/test").body("{\"foo\":\"bar\"}").build();
        PreDecoratorRequest preDecoratorRequest = PreDecoratorRequest.builder().request(originalRequest).build();
        DecoratorResponse response = PreDecoratorResponseFactory.makeResponse(preDecoratorRequest.leaveOriginalRequestUnmodified());
        assertEquals(response.getStatusCode(), 204);
        assertNull(response.getHeaders());
        assertNull(response.getBody());
    }
}
