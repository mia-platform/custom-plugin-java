package eu.miaplatform.decorators;

import java.util.Map;

public class ChangeOriginalRequestResponse extends Response{
    private static final int LEAVE_UNCHANGED_REQUEST_STATUS_CODE = 200;

    ChangeOriginalRequestResponse(Map<String, String> headers, Request body) {
        super(headers, LEAVE_UNCHANGED_REQUEST_STATUS_CODE, body);
    }
}
