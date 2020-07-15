package eu.miaplatform.decorators;

public class DecoratorResponseFactory {
    public static Response makeResponse(PreDecoratorRequest preDecoratorRequest) {
        if (preDecoratorRequest == null) {
            return new LeaveOriginalRequestUnmodifiedResponse();
        } else {
            return new ChangeOriginalRequestResponse(preDecoratorRequest.getOriginalRequestHeaders(), preDecoratorRequest.getResponseBody());
        }
    }
}
