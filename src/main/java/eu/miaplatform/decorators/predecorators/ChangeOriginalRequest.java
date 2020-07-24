package eu.miaplatform.decorators.predecorators;

import eu.miaplatform.decorators.DecoratorRequest;
import eu.miaplatform.decorators.DecoratorResponse;

import static eu.miaplatform.decorators.constants.DecoratorConstants.CHANGE_ORIGINAL_STATUS_CODE;
import static eu.miaplatform.decorators.constants.DecoratorConstants.DEFAULT_HEADERS;

public class ChangeOriginalRequest<T, U> extends DecoratorResponse<U> {
    public ChangeOriginalRequest(DecoratorRequest<T> request) {
        //FIXME usare il tipo generico corretto
        super(CHANGE_ORIGINAL_STATUS_CODE, DEFAULT_HEADERS, (U) request.getBody());
    }
}
