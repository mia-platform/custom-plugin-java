package eu.miaplatform.customplugin;

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
}
