package eu.miaplatform.service;

import lombok.*;

@Data
@AllArgsConstructor
public class ServiceOptions extends InitServiceOptions {
    private ReturnAs returnAs;
    private int[] allowedStatusCodes;
    private boolean isMiaHeaderInjected;

    public ServiceOptions() {
        super();
        this.returnAs = ReturnAs.JSON;
        this.allowedStatusCodes = new int[]{200, 201, 202};
        this.isMiaHeaderInjected = true;
    }
}
