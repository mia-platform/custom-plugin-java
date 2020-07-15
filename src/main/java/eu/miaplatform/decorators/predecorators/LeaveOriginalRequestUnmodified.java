package eu.miaplatform.decorators.predecorators;

import eu.miaplatform.decorators.DecoratorResponse;

import static eu.miaplatform.decorators.constants.DecoratorConstants.LEAVE_ORIGINAL_UNCHANGED_STATUS_CODE;

public class LeaveOriginalRequestUnmodified extends DecoratorResponse {
    public LeaveOriginalRequestUnmodified() {
        super(null, LEAVE_ORIGINAL_UNCHANGED_STATUS_CODE, null);
    }
}
