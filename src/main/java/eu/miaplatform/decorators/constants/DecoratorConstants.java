package eu.miaplatform.decorators.constants;

import java.util.HashMap;
import java.util.Map;

public final class DecoratorConstants {
    public static final int CHANGE_ORIGINAL_STATUS_CODE = 200;
    public static final int LEAVE_ORIGINAL_UNCHANGED_STATUS_CODE = 204;
    public static final int ABORT_CHAIN_STATUS_CODE = 418;
    public static final String CONTENT_TYPE_HEADER_KEY = "Content-Type";
    public static final String CONTENT_TYPE_HEADER_VALUE = "application/json; charset=utf-8";
    public static final Map<String, String> DEFAULT_HEADERS = new HashMap<String, String>() {{
        put(CONTENT_TYPE_HEADER_KEY, CONTENT_TYPE_HEADER_VALUE);
    }};
}

