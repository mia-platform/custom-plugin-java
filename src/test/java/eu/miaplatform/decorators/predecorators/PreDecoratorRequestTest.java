package eu.miaplatform.decorators.predecorators;

import eu.miaplatform.decorators.SampleBody;
import org.junit.Test;

import static org.junit.Assert.*;

public class PreDecoratorRequestTest {
    @Test
    public void originalRequestUnmodified() {
        SampleBody sampleBody = SampleBody.builder().foo("foo").bar(42).baz(true).build();
        PreDecoratorRequest<SampleBody> preDecoratorRequest = PreDecoratorRequest.<SampleBody>builder().method("GET").path("/test").body(sampleBody).build();

        PreDecoratorRequest<SampleBody> updatedRequest = preDecoratorRequest.leaveOriginalRequestUnmodified();

        assertNull(updatedRequest);
    }

    @Test
    public void originalRequestGetsModified() {
        SampleBody originalBody = SampleBody.builder().foo("foo").bar(42).baz(true).build();
        SampleBody updatedBody = SampleBody.builder().foo("bar").bar(1).baz(false).build();

        PreDecoratorRequest<SampleBody> preDecoratorRequest = PreDecoratorRequest.<SampleBody>builder().method("GET").path("/test").body(originalBody).build();

        PreDecoratorRequest<SampleBody> updatedRequest = preDecoratorRequest.changeOriginalRequest()
                .setBody(updatedBody)
                .build();

        assertNotEquals(preDecoratorRequest.getOriginalRequestBody(), updatedRequest.getRequest().getBody());
        assertEquals(preDecoratorRequest.getOriginalRequestBody(), originalBody);
        assertEquals(updatedRequest.getRequest().getBody(), updatedBody);
    }
}
