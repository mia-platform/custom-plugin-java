package eu.miaplatform.decorators.predecorators;

import eu.miaplatform.decorators.DecoratorResponse;

public class PreDecoratorResponseFactory {
    public static DecoratorResponse makeResponse(PreDecoratorRequest preDecoratorRequest) {
        if (preDecoratorRequest == null) {
            return new LeaveOriginalRequestUnmodified();
        } else {
            return new ChangeOriginalRequest(preDecoratorRequest.getResponseBody());
        }
    }
}
