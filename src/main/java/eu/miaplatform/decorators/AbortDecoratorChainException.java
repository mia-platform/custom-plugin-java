package eu.miaplatform.decorators;

import java.util.Map;

public class AbortDecoratorChainException extends Exception {
    public AbortDecoratorChainException(int finalStatusCode, String finalBody, Map<String, String> finalHeaders) {
        super("Decorator chain aborted with status code " + finalStatusCode);
    }
}
