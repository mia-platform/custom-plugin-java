package eu.miaplatform.service;

import com.google.gson.JsonObject;
import okhttp3.*;

import java.io.IOException;
import java.util.*;

public class Service {
    private static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json; charset=utf-8";
    private static final MediaType JSON = MediaType.parse(APPLICATION_JSON_CHARSET_UTF_8);
    private String serviceName;
    private Headers headers;
    private InitServiceOptions options;

    private OkHttpClient client = new OkHttpClient.Builder()
            .connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT))
            .build();

    public Service(String serviceName, InitServiceOptions options) {
        this.serviceName = serviceName;
        this.headers = Headers.of(options.getHeaders());
        this.options = options;
    }

    private HttpUrl buildUrl(String path, String queryString, ServiceOptions options) {
        return new HttpUrl.Builder()
                .scheme(options.getProtocol().toString())
                .host(this.serviceName)
                .port(options.getPort())
                .addPathSegments(path)
                .query(queryString)
                .build();
    }

    public Response get(String path, String queryString, ServiceOptions options) throws IOException {
        HttpUrl url = buildUrl(path, queryString, options);
        Request request = new Request.Builder()
                .headers(this.headers)
                .url(url)
                .build();
        return client.newCall(request).execute();
    }

    public Response post(String path, JsonObject body, String queryString, ServiceOptions options) throws IOException {
        HttpUrl url = buildUrl(path, queryString, options);
        RequestBody reqBody = RequestBody.create(JSON, body.toString());
        Request request =  new Request.Builder()
                .headers(this.headers)
                .url(url)
                .post(reqBody)
                .build();
        return client.newCall(request).execute();
    }

    public Response put(String path, JsonObject body, String queryString, ServiceOptions options) throws IOException {
        HttpUrl url = buildUrl(path, queryString, options);
        RequestBody reqBody = RequestBody.create(JSON, body.toString());
        Request request =  new Request.Builder()
                .headers(this.headers)
                .url(url)
                .put(reqBody)
                .build();
        return client.newCall(request).execute();
    }

    public Response patch(String path, JsonObject body, String queryString, ServiceOptions options) throws IOException {
        HttpUrl url = buildUrl(path, queryString, options);
        RequestBody reqBody = RequestBody.create(JSON, body.toString());
        Request request =  new Request.Builder()
                .headers(this.headers)
                .url(url)
                .patch(reqBody)
                .build();
        return client.newCall(request).execute();
    }

    public Response delete(String path, JsonObject body, String queryString, ServiceOptions options) throws IOException {
        HttpUrl url = buildUrl(path, queryString, options);
        RequestBody reqBody = RequestBody.create(JSON, body.toString());
        Request request =  new Request.Builder()
                .headers(this.headers)
                .url(url)
                .delete(reqBody)
                .build();
        return client.newCall(request).execute();
    }
}
