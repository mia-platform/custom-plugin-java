package eu.miaplatform.customplugin;

import eu.miaplatform.service.environment.EnvConfiguration;
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
        String microserviceNameKey = EnvConfiguration.getInstance().get(MICROSERVICE_GATEWAY_SERVICE_NAME_KEY);
        if (microserviceNameKey == null) {
            return null;
        }
        return new Service(microserviceNameKey, options);
    }
}
