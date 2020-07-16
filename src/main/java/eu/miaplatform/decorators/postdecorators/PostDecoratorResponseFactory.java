package eu.miaplatform.decorators.postdecorators;

import eu.miaplatform.decorators.DecoratorResponse;

public class PostDecoratorResponseFactory {
    public static DecoratorResponse makeResponse(PostDecoratorRequest postDecoratorRequest) {
        if (postDecoratorRequest == null) {
            return new LeaveOriginalResponseUnmodified();
        } else {
            return new ChangeOriginalResponse(postDecoratorRequest.getResponseBody());
        }
    }
}

