package eu.miaplatform.service.environment;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class EnvConfiguration {
    private Map<String, String> envVariables;
    private final static String LIST_SEPARATOR = ",";
    private static EnvConfiguration envConfigurationInstance = null;

    private EnvConfiguration() {}

    public static EnvConfiguration getInstance()
    {
        if (envConfigurationInstance == null) {
            envConfigurationInstance = new EnvConfiguration();
        }
        return envConfigurationInstance;
    }

    public static void parseEnvironment(EnvVariable[] schema) throws InvalidEnvConfigurationException {
        Map<String, String> envVariables = customPluginRequiredVariables();
        for (EnvVariable envVariable : schema) {
            if (System.getenv(envVariable.getKey()) != null) {
                envVariables.put(envVariable.getKey(), System.getenv(envVariable.getKey()));
                continue;
            }

            if (envVariable.isRequired()) {
                throw new InvalidEnvConfigurationException("Required environment variable not found");
            }

            if (envVariable.getDefaultValue() != null) {
                envVariables.put(envVariable.getKey(), envVariable.getDefaultValue());
            }
        }
        getInstance().setEnvVariables(envVariables);
    }

    public static void parseEnvironment() throws InvalidEnvConfigurationException {
        Map<String, String> envVariables = customPluginRequiredVariables();
        getInstance().setEnvVariables(envVariables);
    }

    private static Map<String, String> customPluginRequiredVariables() throws InvalidEnvConfigurationException {
        Map<String, String> requiredEnvVariables = new HashMap<>();
        requiredEnvVariables.put("USERID_HEADER_KEY", getRequired("USERID_HEADER_KEY"));
        requiredEnvVariables.put("GROUPS_HEADER_KEY", getRequired("GROUPS_HEADER_KEY"));
        requiredEnvVariables.put("CLIENTTYPE_HEADER_KEY", getRequired("CLIENTTYPE_HEADER_KEY"));
        requiredEnvVariables.put("BACKOFFICE_HEADER_KEY", getRequired("BACKOFFICE_HEADER_KEY"));
        requiredEnvVariables.put("MICROSERVICE_GATEWAY_SERVICE_NAME", getRequired("MICROSERVICE_GATEWAY_SERVICE_NAME"));
        return requiredEnvVariables;
    }

    private static String getRequired(String envKey) throws InvalidEnvConfigurationException {
        String value = System.getenv(envKey);
        if (value == null || value.isEmpty()) {
            throw new InvalidEnvConfigurationException("Required environment variable not found");
        }
        return value;
    }

    public String get(String envKey) {
        return envVariables.get(envKey);
    }
}
