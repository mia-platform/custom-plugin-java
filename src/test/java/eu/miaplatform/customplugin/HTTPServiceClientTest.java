package eu.miaplatform.customplugin;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import eu.miaplatform.service.*;
import okhttp3.Headers;
import okhttp3.Response;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertNotNull;

public class HTTPServiceClientTest {
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(3000);

    @Test
    public void testGet() throws IOException {
        String url = "/test?foo=bar";
        String responseBody = "{\"result\":\"ok\"}";
        stubFor(WireMock.get(urlEqualTo(url))
                .withQueryParam("foo", WireMock.equalTo("bar"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(responseBody)));

        JsonObject obj = new JsonObject();
        Service service = new Service("localhost", obj, new InitServiceOptions(3000, Protocol.HTTP, obj, ""));
        Response response = service.get("test", "foo=bar", new ServiceOptions());

        assertEquals(response.code(), 200);
        assertNotNull(response.body());
        assertEquals(response.body().string(), responseBody);
        verify(getRequestedFor(urlEqualTo(url))
                .withQueryParam("foo", WireMock.equalTo("bar")));
    }

    @Test
    public void parseHeaders() {
        JsonObject obj = new JsonObject();
        obj.add("foo", new Gson().toJsonTree("bar"));
        Service service = new Service("foo", obj, new InitServiceOptions(123, Protocol.HTTP, obj, ""));
        Headers headers = service.parseHeaders();
        assertEquals(headers.toString(), "foo: \"bar\"\n");
    }

    @Test
    public void get() throws IOException {
        JsonObject obj = new JsonObject();
        Service service = new Service("localhost", obj, new InitServiceOptions(3000, Protocol.HTTP, obj, ""));
        Response response = service.get("users", "foo=bar", new ServiceOptions());
        System.out.println(response.body().string());
    }

    @Test
    public void post() throws IOException {
        JsonObject obj = new JsonObject();
        JsonObject body = new JsonObject();
        body.add("firstname", new Gson().toJsonTree("bar"));

        Service service = new Service("localhost", obj, new InitServiceOptions(3000, Protocol.HTTP, obj, ""));
        Response response = service.post("users", body, "foo=bar", new ServiceOptions());

        System.out.println(response.body().string());
    }
}
