package eu.miaplatform.decorators;

import lombok.Data;

import java.util.Map;

import static eu.miaplatform.decorators.constants.DecoratorConstants.ABORT_CHAIN_STATUS_CODE;

@Data
public class AbortChainResponse extends DecoratorResponse{
    private int finalStatusCode;

    public AbortChainResponse(int finalStatusCode, String finalBody, Map<String, String> finalHeaders) {
        super(finalHeaders, ABORT_CHAIN_STATUS_CODE, finalBody);
        this.finalStatusCode = finalStatusCode;
    }
}
