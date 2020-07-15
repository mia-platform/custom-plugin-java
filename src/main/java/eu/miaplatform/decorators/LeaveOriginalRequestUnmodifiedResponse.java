package eu.miaplatform.decorators;

public class LeaveOriginalRequestUnmodifiedResponse extends Response {
    private static final int LEAVE_UNCHANGED_REQUEST_STATUS_CODE = 204;

    public LeaveOriginalRequestUnmodifiedResponse() {
        super(null, LEAVE_UNCHANGED_REQUEST_STATUS_CODE, null);
    }
}
