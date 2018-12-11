package eu.miaplatform.customplugin;

public class ServiceClientFactory {

    static ServiceClient getCRUDServiceClient(String apiPath, String apiSecret, CustomPluginHeadersPropagator headersPropagator) {
        return new CRUDServiceClient(apiPath, apiSecret, headersPropagator);
    }
}
