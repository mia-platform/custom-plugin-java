package eu.miaplatform.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import okhttp3.*;

import java.io.IOException;
import java.util.*;

public class Service {
    private String serviceName;
    private JsonObject requestMiaHeaders;
    private InitServiceOptions options;

    private OkHttpClient client = new OkHttpClient.Builder()
            .connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT))
            .build();

    public Service(String serviceName, JsonObject requestMiaHeaders, InitServiceOptions options) {
        this.serviceName = serviceName;
        this.requestMiaHeaders = requestMiaHeaders;
        this.options = options;
    }

    public Headers parseHeaders() {
        Headers headers = new Headers.Builder().build();
        Set<Map.Entry<String, JsonElement>> entries = requestMiaHeaders.entrySet();
        for (Map.Entry<String, JsonElement> entry : entries) {
            headers = new Headers.Builder().add(entry.getKey(), String.valueOf(entry.getValue())).build();
        }
        return headers;
    }

    public Response get(String path, String queryString, ServiceOptions options) throws IOException {
        HttpUrl url = new HttpUrl.Builder()
                .scheme(options.getProtocol().toString())
                .host(serviceName)
                .port(options.getPort())
                .addPathSegments(path)
                .query(queryString)
                .build();

        Request request = new Request.Builder()
                .headers(parseHeaders())
                .url(url)
                .build();

        return client.newCall(request).execute();
    }
}
