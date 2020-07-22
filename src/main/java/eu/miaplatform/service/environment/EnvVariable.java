package eu.miaplatform.service.environment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EnvVariable {
    private String key;
    private boolean required;
    private String defaultValue;

    public EnvVariable(String key, boolean required) {
        this.key = key;
        this.required = required;
    }
}
