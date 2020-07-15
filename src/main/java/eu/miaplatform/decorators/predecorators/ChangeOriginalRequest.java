package eu.miaplatform.decorators.predecorators;

import eu.miaplatform.decorators.DecoratorRequest;
import eu.miaplatform.decorators.DecoratorResponse;

import java.util.Map;

import static eu.miaplatform.decorators.constants.DecoratorConstants.CHANGE_ORIGINAL_STATUS_CODE;

public class ChangeOriginalRequest extends DecoratorResponse {
    ChangeOriginalRequest(Map<String, String> headers, DecoratorRequest body) {
        super(headers, CHANGE_ORIGINAL_STATUS_CODE, body);
    }
}
