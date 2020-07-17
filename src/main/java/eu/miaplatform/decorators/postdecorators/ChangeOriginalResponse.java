package eu.miaplatform.decorators.postdecorators;

import eu.miaplatform.decorators.DecoratorResponse;

import static eu.miaplatform.decorators.constants.DecoratorConstants.CHANGE_ORIGINAL_STATUS_CODE;
import static eu.miaplatform.decorators.constants.DecoratorConstants.DEFAULT_HEADERS;

public class ChangeOriginalResponse extends DecoratorResponse {
    ChangeOriginalResponse(DecoratorResponse body) {
        super(CHANGE_ORIGINAL_STATUS_CODE, DEFAULT_HEADERS, body);
    }
}
