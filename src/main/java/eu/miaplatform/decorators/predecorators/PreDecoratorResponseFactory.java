package eu.miaplatform.decorators.predecorators;

import eu.miaplatform.decorators.AbortChainResponse;
import eu.miaplatform.decorators.DecoratorResponse;

import java.util.Map;

import static eu.miaplatform.decorators.constants.DecoratorConstants.DEFAULT_HEADERS;

public class PreDecoratorResponseFactory {
    public static DecoratorResponse makeResponse(PreDecoratorRequest preDecoratorRequest) {
        if (preDecoratorRequest == null) {
            return new LeaveOriginalRequestUnmodified();
        } else {
            return new ChangeOriginalRequest(preDecoratorRequest.getResponseBody());
        }
    }

    public static DecoratorResponse abortChain(int finalStatusCode) {
        DecoratorResponse body = DecoratorResponse.builder().statusCode(finalStatusCode).build();
        return new AbortChainResponse(body, DEFAULT_HEADERS);
    }

    public static DecoratorResponse abortChain(int finalStatusCode, Object finalBody, Map<String, String> finalHeaders) {
        DecoratorResponse body = DecoratorResponse.builder().statusCode(finalStatusCode).body(finalBody).build();
        return new AbortChainResponse(body, finalHeaders);
    }
}
