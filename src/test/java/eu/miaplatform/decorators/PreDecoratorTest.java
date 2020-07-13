package eu.miaplatform.decorators;

import org.junit.Test;

import static org.junit.Assert.*;

public class PreDecoratorTest {
    @Test
    public void originalRequestDoesNotGetModified() {
        Request originalRequest = Request.builder().method("GET").path("/test").body("{\"foo\":\"bar\"}").build();
        PreDecoratorRequest preDecoratorRequest = PreDecoratorRequest.builder().request(originalRequest).build();

        PreDecoratorRequest updatedRequest = preDecoratorRequest.changeOriginalRequest().setBody("{\"baz\":\"bam\"}");

        assertNotEquals(preDecoratorRequest.getOriginalRequestBody(), updatedRequest.getOriginalRequest().getBody());
        assertEquals(preDecoratorRequest.getOriginalRequestBody(), "{\"foo\":\"bar\"}");
        assertEquals(updatedRequest.getOriginalRequest().getBody(), "{\"baz\":\"bam\"}");
    }
}
