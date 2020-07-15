package eu.miaplatform.service;

import lombok.Data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class EnvConfiguration {
    private Map<String, String> defaultEnv;

    public EnvConfiguration() {
        defaultEnv = new HashMap<>();
        defaultEnv.put("USERID_HEADER_KEY", "userid-header-key");
        defaultEnv.put("GROUPS_HEADER_KEY", "groups-header-key");
        defaultEnv.put("CLIENTTYPE_HEADER_KEY", "clienttype-header-key");
        defaultEnv.put("BACKOFFICE_HEADER_KEY", "backoffice-header-key");
        defaultEnv.put("GROUP_TO_GREET", "group-to-greet");
        defaultEnv.put("MICROSERVICE_GATEWAY_SERVICE_NAME", "microservice-gateway");
    }

    public String get(String envVariable) throws EnvConfigurationException {
        String value = System.getenv(envVariable);
        if (value == null) {
            throw new EnvConfigurationException();
        }
        return value;
    }

    public String get(String envVariable, String defaultValue) {
        return System.getenv(envVariable) == null ? defaultValue : System.getenv(envVariable);
    }

    public String getAndRequire(String envVariable) {
        String value = System.getenv(envVariable);
        if (value == null || value.equals("")) {
            throw new IllegalArgumentException("Missing required environment variable: " + envVariable);
        }
        return value;
    }

    public int getInt(String envVariable) throws NumberFormatException, EnvConfigurationException {
        String value = System.getenv(envVariable);
        if (value == null) {
            throw new EnvConfigurationException();
        }
        return Integer.parseInt(System.getenv(envVariable));
    }

    public List<String> getStringList(String envVariable) {
        String value = getAndRequire(envVariable);
        String LIST_SEPARATOR = ",";
        return Arrays.asList(value.split(LIST_SEPARATOR));
    }

    public List<String> getAndRequireStringList(String envVariable) {
        List<String> values = getStringList(envVariable);
        if (values.size() == 0) {
            throw new IllegalArgumentException("Unexpected empty list for " + envVariable);
        } else {
            return values;
        }
    }
}
