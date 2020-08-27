package eu.miaplatform.service.environment;
import com.github.stefanbirkner.systemlambda.Statement;
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
    public void singletonTestWithDefaultVariables() throws Exception {
        withEnvironmentVariable("USERID_HEADER_KEY", "USERID_HEADER_VAL")
                .and("GROUPS_HEADER_KEY", "GROUPS_HEADER_VAL")
                .and("CLIENTTYPE_HEADER_KEY", "CLIENTTYPE_HEADER_VAL")
                .and("BACKOFFICE_HEADER_KEY", "BACKOFFICE_HEADER_VAL")
                .and("MICROSERVICE_GATEWAY_SERVICE_NAME", "MICROSERVICE_GATEWAY_SERVICE_NAME")
                .execute((Statement) EnvConfiguration::parseEnvironment);
        EnvConfiguration newEnvConfiguration = EnvConfiguration.getInstance();
        Map<String, String> envVariables = newEnvConfiguration.getEnvVariables();
        assertSame(envVariables.size(), 5);
        assertEquals(envVariables.get("USERID_HEADER_KEY"), "USERID_HEADER_VAL");
        assertEquals(envVariables.get("GROUPS_HEADER_KEY"), "GROUPS_HEADER_VAL");
        assertEquals(envVariables.get("CLIENTTYPE_HEADER_KEY"), "CLIENTTYPE_HEADER_VAL");
        assertEquals(envVariables.get("BACKOFFICE_HEADER_KEY"), "BACKOFFICE_HEADER_VAL");
        assertEquals(envVariables.get("MICROSERVICE_GATEWAY_SERVICE_NAME"), "MICROSERVICE_GATEWAY_SERVICE_NAME");
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
                .and("GROUPS_HEADER_KEY", "GROUPS_HEADER_VAL")
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
                    assertSame(envVariables.size(), 6);
                    assertEquals(envVariables.get("USERID_HEADER_KEY"), "USERID_HEADER_VAL");
                    assertEquals(envVariables.get("GROUPS_HEADER_KEY"), "GROUPS_HEADER_VAL");
                    assertEquals(envVariables.get("CLIENTTYPE_HEADER_KEY"), "CLIENTTYPE_HEADER_VAL");
                    assertEquals(envVariables.get("BACKOFFICE_HEADER_KEY"), "BACKOFFICE_HEADER_VAL");
                    assertEquals(envVariables.get("MICROSERVICE_GATEWAY_SERVICE_NAME"), "MICROSERVICE_GATEWAY_SERVICE_NAME");
                    assertEquals(envVariables.get("foo"), "foovalue");
                    assertNull(envVariables.get("bar"));
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
                    assertSame(envVariables.size(), 7);
                    assertEquals(envVariables.get("USERID_HEADER_KEY"), "USERID_HEADER_VAL");
                    assertEquals(envVariables.get("GROUPS_HEADER_KEY"), "GROUPS_HEADER_VAL");
                    assertEquals(envVariables.get("CLIENTTYPE_HEADER_KEY"), "CLIENTTYPE_HEADER_VAL");
                    assertEquals(envVariables.get("BACKOFFICE_HEADER_KEY"), "BACKOFFICE_HEADER_VAL");
                    assertEquals(envVariables.get("MICROSERVICE_GATEWAY_SERVICE_NAME"), "MICROSERVICE_GATEWAY_SERVICE_NAME");
                    assertEquals(envVariables.get("foo"), "foovalue");
                    assertEquals(envVariables.get("bar"), "barvalue");
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
