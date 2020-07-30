package eu.miaplatform.service;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class ServiceOptions extends InitServiceOptions {
    @Builder.Default
    private ReturnAs returnAs = ReturnAs.JSON;
    @Builder.Default
    private int[] allowedStatusCodes = new int[]{200, 201, 202};
    @Builder.Default
    private boolean isMiaHeaderInjected = true;

    public ServiceOptions() {
        super();
    }
}
