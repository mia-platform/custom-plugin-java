package eu.miaplatform.decorators.postdecorators;

import eu.miaplatform.decorators.DecoratorResponse;

import static eu.miaplatform.decorators.constants.DecoratorConstants.LEAVE_ORIGINAL_UNCHANGED_STATUS_CODE;

public class LeaveOriginalResponseUnmodified extends DecoratorResponse {
    public LeaveOriginalResponseUnmodified() {
        super(null, LEAVE_ORIGINAL_UNCHANGED_STATUS_CODE, null);
    }
}

