package eu.miaplatform.decorators.postdecorators;

import eu.miaplatform.decorators.DecoratorResponse;

import static eu.miaplatform.decorators.constants.DecoratorConstants.DEFAULT_HEADERS;
import static eu.miaplatform.decorators.constants.DecoratorConstants.LEAVE_ORIGINAL_UNCHANGED_STATUS_CODE;

public class LeaveOriginalResponseUnmodified<T> extends DecoratorResponse<T> {
    public LeaveOriginalResponseUnmodified() {
        super(LEAVE_ORIGINAL_UNCHANGED_STATUS_CODE, DEFAULT_HEADERS, null);
    }
}

