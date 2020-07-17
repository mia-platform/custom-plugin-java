package eu.miaplatform.decorators.postdecorators;

import eu.miaplatform.decorators.AbortChainResponse;
import eu.miaplatform.decorators.DecoratorResponse;

import java.util.Map;

import static eu.miaplatform.decorators.constants.DecoratorConstants.DEFAULT_HEADERS;

public class PostDecoratorResponseFactory {
    public static DecoratorResponse makeResponse(PostDecoratorRequest postDecoratorRequest) {
        if (postDecoratorRequest == null) {
            return new LeaveOriginalResponseUnmodified();
        } else {
            return new ChangeOriginalResponse(postDecoratorRequest.getResponseBody());
        }
    }

    public static DecoratorResponse abortChain(int finalStatusCode) {
        return new AbortChainResponse(finalStatusCode, "Decorator chain aborted with status code " + finalStatusCode, DEFAULT_HEADERS);
    }

    public static DecoratorResponse abortChain(int finalStatusCode, String finalBody, Map<String, String> finalHeaders) {
        return new AbortChainResponse(finalStatusCode, finalBody, finalHeaders);
    }
}

