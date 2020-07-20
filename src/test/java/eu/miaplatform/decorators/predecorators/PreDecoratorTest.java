package eu.miaplatform.decorators.predecorators;

import eu.miaplatform.decorators.DecoratorRequest;
import org.junit.Test;

import static org.junit.Assert.*;

public class PreDecoratorTest {
    @Test
    public void originalRequestUnmodified() {
        DecoratorRequest originalRequest = DecoratorRequest.builder().method("GET").path("/test").body("{\"foo\":\"bar\"}").build();
        PreDecoratorRequest preDecoratorRequest = PreDecoratorRequest.builder().method("GET").path("/test").body("{\"foo\":\"bar\"}").build();

        PreDecoratorRequest updatedRequest = preDecoratorRequest.leaveOriginalRequestUnmodified();

        assertNull(updatedRequest);
    }

    @Test
    public void originalRequestGetsModified() {
        DecoratorRequest originalRequest = DecoratorRequest.builder().method("GET").path("/test").body("{\"foo\":\"bar\"}").build();
        PreDecoratorRequest preDecoratorRequest = PreDecoratorRequest.builder().method("GET").path("/test").body("{\"foo\":\"bar\"}").build();

        PreDecoratorRequest updatedRequest = preDecoratorRequest.changeOriginalRequest()
                .setBody("{\"baz\":\"bam\"}")
                .build();

        assertNotEquals(preDecoratorRequest.getOriginalRequestBody(), updatedRequest.getRequest().getBody());
        assertEquals(preDecoratorRequest.getOriginalRequestBody(), "{\"foo\":\"bar\"}");
        assertEquals(updatedRequest.getRequest().getBody(), "{\"baz\":\"bam\"}");
    }
}
