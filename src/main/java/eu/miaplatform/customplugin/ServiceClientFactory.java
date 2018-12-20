package eu.miaplatform.customplugin;

public class ServiceClientFactory {

    public static CRUDServiceClient getCRUDServiceClient(String apiPath, String apiSecret, CustomPluginHeadersPropagator headersPropagator) {
        return new CRUDServiceClientImpl(apiPath, apiSecret, headersPropagator);
    }
    public static CRUDServiceClient getCRUDServiceClient(String apiPath, String apiSecret, int version, CustomPluginHeadersPropagator headersPropagator) {
        return new CRUDServiceClientImpl(apiPath, apiSecret, version, headersPropagator);
    }
}
