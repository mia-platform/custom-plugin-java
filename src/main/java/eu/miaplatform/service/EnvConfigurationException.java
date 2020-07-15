package eu.miaplatform.service;

public class EnvConfigurationException extends Throwable {
    @Override
    public String getMessage() {
        return "Environment variable not found";
    }
}
