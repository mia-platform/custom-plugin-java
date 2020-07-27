package eu.miaplatform.decorators.predecorators;

import eu.miaplatform.decorators.DecoratorRequest;
import eu.miaplatform.decorators.DecoratorResponse;

import static eu.miaplatform.decorators.constants.DecoratorConstants.CHANGE_ORIGINAL_STATUS_CODE;
import static eu.miaplatform.decorators.constants.DecoratorConstants.DEFAULT_HEADERS;

public class ChangeOriginalRequest<U> extends DecoratorResponse<U> {
    public ChangeOriginalRequest(DecoratorRequest<U> request) {
        super(CHANGE_ORIGINAL_STATUS_CODE, DEFAULT_HEADERS, request.getBody());
    }
}
