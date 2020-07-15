package eu.miaplatform.service;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceOptions extends InitServiceOptions {
    @Builder.Default
    private ReturnAs returnAs = ReturnAs.JSON;
    @Builder.Default
    private int[] allowedStatusCodes = new int[]{200, 201, 202};
    @Builder.Default
    private boolean isMiaHeaderInjected = true;
}
