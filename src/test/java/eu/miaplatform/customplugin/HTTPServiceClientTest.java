package eu.miaplatform.customplugin;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.google.gson.*;
import eu.miaplatform.service.*;
import okhttp3.Headers;
import okhttp3.Response;
import org.junit.*;
import org.mockito.Mock;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HTTPServiceClientTest {
    @Mock
    InitServiceOptions initServiceOptions;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(3000);

    private Service service;
    private final String url = "/test";

    @Before
    public void setup() {
        service = new Service("localhost", new JsonObject(), initServiceOptions);
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
    public void testGet() throws IOException {
        String responseBody = "{\"result\":\"ok\"}";
        stubFor(WireMock.get(urlPathEqualTo(url))
                .withQueryParam("id", WireMock.equalTo("1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(responseBody)));

        Response response = service.get("test", "id=1", new ServiceOptions());

        assertEquals(response.code(), 200);
        assertNotNull(response.body());
        assertEquals(response.body().string(), responseBody);
        verify(getRequestedFor(urlPathEqualTo(url))
                .withQueryParam("id", WireMock.equalTo("1")));
    }

    @Test
    public void testPost() throws IOException {
        String requestBody = "{\"foo\":\"bar\"}";
        stubFor(WireMock.post(urlPathEqualTo(url))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(requestBody)));

        JsonObject body = new JsonParser().parse(requestBody).getAsJsonObject();
        Response response = service.post("test", body, "", new ServiceOptions());

        assertEquals(response.code(), 200);
        assertNotNull(response.body());
        assertEquals(response.body().string(), requestBody);
        verify(postRequestedFor(urlPathEqualTo(url)));
    }

    @Test
    public void testPut() throws IOException {
        String requestBody = "{\"foo\":\"bar\"}";
        stubFor(WireMock.put(urlPathEqualTo(url))
                .withQueryParam("id", WireMock.equalTo("1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(requestBody)));

        JsonObject body = new JsonParser().parse(requestBody).getAsJsonObject();
        Response response = service.put("test", body, "id=1", new ServiceOptions());

        assertEquals(response.code(), 200);
        assertNotNull(response.body());
        assertEquals(response.body().string(), requestBody);
        verify(putRequestedFor(urlPathEqualTo(url))
                .withQueryParam("id", WireMock.equalTo("1")));
    }

    @Test
    public void testPatch() throws IOException {
        String requestBody = "{\"$set\":{\"count\":42}}";
        stubFor(WireMock.patch(urlPathEqualTo(url))
                .withQueryParam("id", WireMock.equalTo("1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(requestBody)));

        JsonObject body = new JsonParser().parse(requestBody).getAsJsonObject();
        Response response = service.patch("test", body, "id=1", new ServiceOptions());

        assertEquals(response.code(), 200);
        assertNotNull(response.body());
        assertEquals(response.body().string(), requestBody);
        verify(patchRequestedFor(urlPathEqualTo(url))
                .withQueryParam("id", WireMock.equalTo("1")));
    }

    @Test
    public void testDelete() throws IOException {
        String responseBody = "{\"result\":\"ok\"}";

        stubFor(WireMock.delete(urlPathEqualTo(url))
                .withQueryParam("id", WireMock.equalTo("1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(responseBody)));

        Response response = service.delete("test", new JsonObject(), "id=1", new ServiceOptions());

        assertEquals(response.code(), 200);
        assertNotNull(response.body());
        assertEquals(response.body().string(), responseBody);
        verify(deleteRequestedFor(urlPathEqualTo(url))
                .withQueryParam("id", WireMock.equalTo("1")));
    }
}
