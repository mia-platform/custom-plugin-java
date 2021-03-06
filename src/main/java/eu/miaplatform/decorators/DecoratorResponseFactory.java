package eu.miaplatform.decorators;

import eu.miaplatform.decorators.postdecorators.*;
import eu.miaplatform.decorators.predecorators.*;

import java.io.Serializable;
import java.util.Map;

import static eu.miaplatform.decorators.constants.DecoratorConstants.DEFAULT_HEADERS;

public class DecoratorResponseFactory {
    public static DecoratorResponse makePreDecoratorResponse(PreDecoratorRequest preDecoratorRequest) {
        if (preDecoratorRequest == null) {
            return new LeaveOriginalRequestUnmodified();
        }
        return new ChangeOriginalRequest(preDecoratorRequest.getRequest());

    }

    public static DecoratorResponse makePostDecoratorResponse(PostDecoratorRequest postDecoratorRequest) {
        if (postDecoratorRequest == null) {
            return new LeaveOriginalResponseUnmodified();
        }
        return new ChangeOriginalResponse(postDecoratorRequest.getResponse());
    }

    public static DecoratorResponse abortChain(int finalStatusCode) {
        DecoratorResponse body = DecoratorResponse.builder().statusCode(finalStatusCode).build();
        return new AbortChainResponse(body, DEFAULT_HEADERS);
    }

    public static DecoratorResponse abortChain(int finalStatusCode, Serializable finalBody, Map<String, String> finalHeaders) {
        DecoratorResponse body = DecoratorResponse.builder().statusCode(finalStatusCode).body(finalBody).build();
        return new AbortChainResponse(body, finalHeaders);
    }
}
