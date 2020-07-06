package eu.miaplatform.customplugin;

import com.google.gson.JsonObject;
import eu.miaplatform.service.InitServiceOptions;
import eu.miaplatform.service.Service;
import okhttp3.Headers;

import java.util.Map;

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

    public static Service getServiceProxy(String serviceName, InitServiceOptions options) {
        return new Service(serviceName, options);
    }


}
