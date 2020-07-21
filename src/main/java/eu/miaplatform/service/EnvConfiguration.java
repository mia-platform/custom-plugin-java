package eu.miaplatform.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnvConfiguration {
    private Map<String, String> envVariables;
    private final static String LIST_SEPARATOR = ",";

    public EnvConfiguration() {
        envVariables = new HashMap<>();
        envVariables.put("USERID_HEADER_KEY", "userid-header-key");
        envVariables.put("GROUPS_HEADER_KEY", "groups-header-key");
        envVariables.put("CLIENTTYPE_HEADER_KEY", "clienttype-header-key");
        envVariables.put("BACKOFFICE_HEADER_KEY", "backoffice-header-key");
        envVariables.put("GROUP_TO_GREET", "group-to-greet");
        envVariables.put("MICROSERVICE_GATEWAY_SERVICE_NAME", "microservice-gateway");
    }

    public void parseEnvironment() throws EnvConfigurationException {
        for (String variableName : envVariables.keySet()) {
            envVariables.put(variableName, get(variableName));
        }
    }

    public static String get(String envVariable) throws EnvConfigurationException {
        String value = System.getenv(envVariable);
        if (value == null) {
            throw new EnvConfigurationException();
        }
        return value;
    }

    public static String get(String envVariable, String defaultValue) {
        return System.getenv(envVariable) == null ? defaultValue : System.getenv(envVariable);
    }

    public void put(String envVariable, String value) {
        envVariables.put(envVariable, value);
    }

    public static String getAndRequire(String envVariable) {
        String value = System.getenv(envVariable);
        if (value == null || value.equals("")) {
            throw new IllegalArgumentException("Missing required environment variable: " + envVariable);
        }
        return value;
    }

    public static int getInt(String envVariable) throws NumberFormatException, EnvConfigurationException {
        String value = System.getenv(envVariable);
        if (value == null) {
            throw new EnvConfigurationException();
        }
        return Integer.parseInt(System.getenv(envVariable));
    }

    public static List<String> getStringList(String envVariable) {
        String value = getAndRequire(envVariable);
        return Arrays.asList(value.split(LIST_SEPARATOR));
    }

    public static List<String> getAndRequireStringList(String envVariable) {
        List<String> values = getStringList(envVariable);
        if (values.size() == 0) {
            throw new IllegalArgumentException("Unexpected empty list for " + envVariable);
        } else {
            return values;
        }
    }
}
