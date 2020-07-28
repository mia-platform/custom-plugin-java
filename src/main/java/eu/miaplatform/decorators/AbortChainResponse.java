package eu.miaplatform.decorators;

import lombok.Data;

import java.util.Map;

import static eu.miaplatform.decorators.constants.DecoratorConstants.ABORT_CHAIN_STATUS_CODE;

@Data
public class AbortChainResponse extends DecoratorResponse{
    public AbortChainResponse(Object finalBody, Map<String, String> finalHeaders) {
        super(ABORT_CHAIN_STATUS_CODE, finalHeaders, finalBody);
    }
}
