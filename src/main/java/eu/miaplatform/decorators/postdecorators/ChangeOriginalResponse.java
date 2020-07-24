package eu.miaplatform.decorators.postdecorators;

import eu.miaplatform.decorators.DecoratorResponse;

import static eu.miaplatform.decorators.constants.DecoratorConstants.CHANGE_ORIGINAL_STATUS_CODE;
import static eu.miaplatform.decorators.constants.DecoratorConstants.DEFAULT_HEADERS;

public class ChangeOriginalResponse<U> extends DecoratorResponse<U> {
    public ChangeOriginalResponse(DecoratorResponse<U> response) {
        super(CHANGE_ORIGINAL_STATUS_CODE, DEFAULT_HEADERS, response.getBody());
    }
}
