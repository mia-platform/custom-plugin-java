package eu.miaplatform.decorators;

import org.junit.Test;

import static org.junit.Assert.*;

public class PreDecoratorTest {
    @Test
    public void originalRequestUnmodified() {
        Request originalRequest = Request.builder().method("GET").path("/test").body("{\"foo\":\"bar\"}").build();
        PreDecoratorRequest preDecoratorRequest = PreDecoratorRequest.builder().request(originalRequest).build();

        PreDecoratorRequest updatedRequest = preDecoratorRequest.leaveOriginalRequestUnmodified();

        assertEquals(preDecoratorRequest.getOriginalRequestBody(), "{\"foo\":\"bar\"}");
        assertEquals(updatedRequest.getOriginalRequestBody(), preDecoratorRequest.getOriginalRequestBody());
    }

    @Test
    public void originalRequestGetsModified() {
        Request originalRequest = Request.builder().method("GET").path("/test").body("{\"foo\":\"bar\"}").build();
        PreDecoratorRequest preDecoratorRequest = PreDecoratorRequest.builder().request(originalRequest).build();

        PreDecoratorRequest updatedRequest = preDecoratorRequest.changeOriginalRequest()
                .setBody("{\"baz\":\"bam\"}")
                .build();

        assertNotEquals(preDecoratorRequest.getOriginalRequestBody(), updatedRequest.getOriginalRequest().getBody());
        assertEquals(preDecoratorRequest.getOriginalRequestBody(), "{\"foo\":\"bar\"}");
        assertEquals(updatedRequest.getOriginalRequest().getBody(), "{\"baz\":\"bam\"}");
    }

}
