package eu.miaplatform.decorators.postdecorators;

import eu.miaplatform.decorators.DecoratorResponse;

import static eu.miaplatform.decorators.constants.DecoratorConstants.CHANGE_ORIGINAL_STATUS_CODE;
import static eu.miaplatform.decorators.constants.DecoratorConstants.DEFAULT_HEADERS;

public class ChangeOriginalResponse extends DecoratorResponse {
    ChangeOriginalResponse(DecoratorResponse body) {
        super(DEFAULT_HEADERS, CHANGE_ORIGINAL_STATUS_CODE, body);
    }
}
