package eu.miaplatform.decorators.postdecorators;

import eu.miaplatform.decorators.DecoratorResponse;

import java.util.HashMap;

public class PostDecoratorResponseFactory {
    public static DecoratorResponse makeResponse(PostDecoratorRequest postDecoratorRequest) {
        if (postDecoratorRequest == null) {
            return new LeaveOriginalResponseUnmodified();
        } else {
            return new ChangeOriginalResponse(postDecoratorRequest.getResponseBody());
        }
    }
}

