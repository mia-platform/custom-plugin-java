package eu.miaplatform.service.environment;
import org.junit.*;
import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Field;
import java.util.Map;

import static com.github.stefanbirkner.systemlambda.SystemLambda.*;

import static org.junit.Assert.*;

public class EnvConfigurationTest {
    @Before
    public void resetSingletonInstance() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field instance = EnvConfiguration.class.getDeclaredField("envConfigurationInstance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @Test
    public void parseEnvironmentWithDefaultVariables() throws Exception {
        withEnvironmentVariable("USERID_HEADER_KEY", "USERID_HEADER_VAL")
                .and("GROUPS_HEADER_KEY", "GROUPS_HEADER_VAL")
                .and("CLIENTTYPE_HEADER_KEY", "CLIENTTYPE_HEADER_VAL")
                .and("BACKOFFICE_HEADER_KEY", "BACKOFFICE_HEADER_VAL")
                .and("MICROSERVICE_GATEWAY_SERVICE_NAME", "MICROSERVICE_GATEWAY_SERVICE_NAME")
                .execute(() -> {
                    EnvConfiguration.parseEnvironment();
                    Map<String, String> envVariables = EnvConfiguration.getInstance().getEnvVariables();
                    assertEquals(envVariables.size(), 5);
                    for (Map.Entry<String, String> entry : envVariables.entrySet()) {
                        assertNotNull(entry.getKey());
                        assertNotNull(entry.getValue());
                    }
                });
    }

    @Test
    public void parseEnvironmentWithNullVariables() throws Exception {
        withEnvironmentVariable("USERID_HEADER_KEY", null)
                .and("GROUPS_HEADER_KEY", "GROUPS_HEADER_VAL")
                .and("CLIENTTYPE_HEADER_KEY", null)
                .and("BACKOFFICE_HEADER_KEY", "BACKOFFICE_HEADER_VAL")
                .and("MICROSERVICE_GATEWAY_SERVICE_NAME", "MICROSERVICE_GATEWAY_SERVICE_NAME")
                .execute(() -> {
                    Assertions.assertThrows(InvalidEnvConfigurationException.class, EnvConfiguration::parseEnvironment);
                });
    }

    @Test
    public void parseEnvironmentWithAdditionalVariables() throws Exception {
        withEnvironmentVariable("USERID_HEADER_KEY", "USERID_HEADER_VAL")
                .and("GROUPS_HEADER_KEY", "GROUPS_HEADER_VALUE")
                .and("CLIENTTYPE_HEADER_KEY", "CLIENTTYPE_HEADER_VAL")
                .and("BACKOFFICE_HEADER_KEY", "BACKOFFICE_HEADER_VAL")
                .and("MICROSERVICE_GATEWAY_SERVICE_NAME", "MICROSERVICE_GATEWAY_SERVICE_NAME")
                .and("foo", "foovalue")
                .execute(() -> {
                    EnvVariable[] schema = new EnvVariable[2];
                    schema[0] = new EnvVariable("foo", true);
                    schema[1] = new EnvVariable("bar", false);
                    EnvConfiguration.parseEnvironment(schema);
                    Map<String, String> envVariables = EnvConfiguration.getInstance().getEnvVariables();
                    assertEquals(envVariables.size(), 6);
                    for (Map.Entry<String, String> entry : envVariables.entrySet()) {
                        assertNotNull(entry.getKey());
                        assertNotNull(entry.getValue());
                    }
                });
    }

    @Test
    public void parseEnvironmentWithAdditionalNotRequiredVariables() throws Exception {
        withEnvironmentVariable("USERID_HEADER_KEY", "USERID_HEADER_VAL")
                .and("GROUPS_HEADER_KEY", "GROUPS_HEADER_VAL")
                .and("CLIENTTYPE_HEADER_KEY", "CLIENTTYPE_HEADER_VAL")
                .and("BACKOFFICE_HEADER_KEY", "BACKOFFICE_HEADER_VAL")
                .and("MICROSERVICE_GATEWAY_SERVICE_NAME", "MICROSERVICE_GATEWAY_SERVICE_NAME")
                .and("foo", "foovalue")
                .and("bar", "barvalue")
                .execute(() -> {
                    EnvVariable[] schema = new EnvVariable[2];
                    schema[0] = new EnvVariable("foo", false);
                    schema[1] = new EnvVariable("bar", false);
                    EnvConfiguration.parseEnvironment(schema);
                    Map<String, String> envVariables = EnvConfiguration.getInstance().getEnvVariables();
                    assertEquals(envVariables.size(), 7);
                    for (Map.Entry<String, String> entry : envVariables.entrySet()) {
                        assertNotNull(entry.getKey());
                        assertNotNull(entry.getValue());
                    }
                });
    }

    @Test
    public void parseEnvironmentWithNullAdditionalRequiredVariables() throws Exception {
        withEnvironmentVariable("USERID_HEADER_KEY", "USERID_HEADER_VAL")
                .and("GROUPS_HEADER_KEY", "GROUPS_HEADER_VAL")
                .and("CLIENTTYPE_HEADER_KEY", "CLIENTTYPE_HEADER_VAL")
                .and("BACKOFFICE_HEADER_KEY", "BACKOFFICE_HEADER_VAL")
                .and("MICROSERVICE_GATEWAY_SERVICE_NAME", "MICROSERVICE_GATEWAY_SERVICE_NAME")
                .and("foo", null)
                .execute(() -> {
                    EnvVariable[] schema = new EnvVariable[1];
                    schema[0] = new EnvVariable("foo", true);
                    Assertions.assertThrows(InvalidEnvConfigurationException.class, () -> EnvConfiguration.parseEnvironment(schema));
                });
    }
}
