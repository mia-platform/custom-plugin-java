package eu.miaplatform.decorators.postdecorators;

import eu.miaplatform.decorators.DecoratorResponse;

import java.util.Map;

import static eu.miaplatform.decorators.constants.DecoratorConstants.CHANGE_ORIGINAL_STATUS_CODE;

public class ChangeOriginalResponse extends DecoratorResponse {
    ChangeOriginalResponse(Map<String, String> headers, DecoratorResponse body) {
        super(headers, CHANGE_ORIGINAL_STATUS_CODE, body);
    }
}
