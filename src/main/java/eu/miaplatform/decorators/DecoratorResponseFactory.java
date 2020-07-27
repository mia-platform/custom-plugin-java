package eu.miaplatform.decorators;

import eu.miaplatform.decorators.postdecorators.*;
import eu.miaplatform.decorators.predecorators.*;

import java.util.Map;

import static eu.miaplatform.decorators.constants.DecoratorConstants.DEFAULT_HEADERS;

public class DecoratorResponseFactory {
    public static <U> DecoratorResponse<U> makePreDecoratorResponse(PreDecoratorRequest<U> preDecoratorRequest) {
        if (preDecoratorRequest == null) {
            return new LeaveOriginalRequestUnmodified<>();
        }
        return new ChangeOriginalRequest<>(preDecoratorRequest.getRequest());
    }

    public static <T, U> DecoratorResponse<U> makePostDecoratorResponse(PostDecoratorRequest<T, U> postDecoratorRequest) {
        if (postDecoratorRequest == null) {
            return new LeaveOriginalResponseUnmodified<>();
        }
        return new ChangeOriginalResponse<>(postDecoratorRequest.getResponse());
    }

    public static <U> DecoratorResponse<U> abortChain(int finalStatusCode) {
        DecoratorResponse<U> response = DecoratorResponse.<U>builder().statusCode(finalStatusCode).build();
        return new AbortChainResponse<>(response.getBody(), DEFAULT_HEADERS);
    }

    public static <U> DecoratorResponse<U> abortChain(int finalStatusCode, U finalBody, Map<String, String> finalHeaders) {
        DecoratorResponse<U> response = DecoratorResponse.<U>builder().statusCode(finalStatusCode).body(finalBody).build();
        return new AbortChainResponse<>(response.getBody(), finalHeaders);
    }
}
