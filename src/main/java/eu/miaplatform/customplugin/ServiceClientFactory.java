package eu.miaplatform.customplugin;

import com.google.gson.JsonObject;
import eu.miaplatform.service.InitServiceOptions;
import eu.miaplatform.service.Service;

public class ServiceClientFactory {

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

    public static Service getDirectServiceProxy(String serviceName, JsonObject requestMiaHeaders, InitServiceOptions options) {
        return new Service(serviceName, requestMiaHeaders, options);
    }

/*    public static Service getServiceProxy(String microserviceGatewayServiceName, InitServiceOptions options) {

    }*/
}
