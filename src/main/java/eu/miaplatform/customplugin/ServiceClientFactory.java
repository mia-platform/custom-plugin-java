package eu.miaplatform.customplugin;

import eu.miaplatform.service.EnvConfiguration;
import eu.miaplatform.service.EnvConfigurationException;
import eu.miaplatform.service.InitServiceOptions;
import eu.miaplatform.service.Service;

public class ServiceClientFactory {

    public static final String MICROSERVICE_GATEWAY_SERVICE_NAME_KEY = "MICROSERVICE_GATEWAY_SERVICE_NAME";

    public static CRUDServiceClient getCRUDServiceClient(CustomPluginHeadersPropagator headersPropagator) {
        return new CRUDServiceClientImpl(headersPropagator);
    }

    public static CRUDServiceClient getCRUDServiceClient(String apiSecret, CustomPluginHeadersPropagator headersPropagator) {
        return new CRUDServiceClientImpl(headersPropagator);
    }

    public static CRUDServiceClient getCRUDServiceClient(String apiPath, int version, CustomPluginHeadersPropagator headersPropagator) {
        return new CRUDServiceClientImpl(apiPath, version, headersPropagator);
    }

    public static CRUDServiceClient getCRUDServiceClient(String apiPath, String apiSecret, int version, CustomPluginHeadersPropagator headersPropagator) {
        return new CRUDServiceClientImpl(apiPath, apiSecret, version, headersPropagator);
    }

    public static Service getDirectServiceProxy(String serviceName, InitServiceOptions options) {
        return new Service(serviceName, options);
    }

    public static Service getServiceProxy(InitServiceOptions options) {
        EnvConfiguration envConfiguration = new EnvConfiguration();
        try {
            String microserviceNameKey = envConfiguration.get(MICROSERVICE_GATEWAY_SERVICE_NAME_KEY);
            return new Service(microserviceNameKey, options);
        } catch (EnvConfigurationException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
