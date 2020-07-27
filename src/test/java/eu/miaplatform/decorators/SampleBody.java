package eu.miaplatform.decorators;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SampleBody {
    private String foo;
    private int bar;
    private boolean baz;
}
