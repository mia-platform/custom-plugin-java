package eu.miaplatform.service;

import com.google.gson.JsonObject;

public class ServiceOptions extends InitServiceOptions {
    private ReturnAs returnAs;
    private int[] allowedStatusCodes;
    private boolean isMiaHeaderInjected;

    public ServiceOptions(int port, Protocol protocol, JsonObject header, String prefix) {
        super(port, protocol, header, prefix);
    }

    public ServiceOptions(int port, Protocol protocol, JsonObject header, String prefix, ReturnAs returnAs, int[] allowedStatusCodes, boolean isMiaHeaderInjected) {
        super(port, protocol, header, prefix);
        this.returnAs = returnAs;
        this.allowedStatusCodes = allowedStatusCodes;
        this.isMiaHeaderInjected = isMiaHeaderInjected;
    }

    public ReturnAs getReturnAs() {
        return returnAs;
    }

    public int[] getAllowedStatusCodes() {
        return allowedStatusCodes;
    }

    public boolean isMiaHeaderInjected() {
        return isMiaHeaderInjected;
    }
}
