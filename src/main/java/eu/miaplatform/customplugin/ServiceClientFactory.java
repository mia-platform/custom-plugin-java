package eu.miaplatform.customplugin;

public class ServiceClientFactory {

    ServiceClient getCRUDServiceClient(String apiPath, String apiSecret, HeadersPropagator headersPropagator) {
        return new CRUDServiceClient(apiPath, apiSecret, headersPropagator);
    }
}
