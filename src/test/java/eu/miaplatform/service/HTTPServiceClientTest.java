package eu.miaplatform.service;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import eu.miaplatform.service.*;
import okhttp3.Response;
import org.junit.*;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HTTPServiceClientTest {
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(3000);

    private Service service;
    private final String url = "/test";

    @Before
    public void setup() {
        service = new Service("localhost", new InitServiceOptions());
    }

    @Test
    public void testGet() throws IOException {
        String responseBody = "{\"result\":\"ok\"}";
        stubFor(WireMock.get(urlPathEqualTo(url))
                .withQueryParam("id", WireMock.equalTo("1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(responseBody)));

        Response response = service.get("test", "id=1");

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

        Response response = service.post("test", requestBody, "", new ServiceOptions());

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

        Response response = service.put("test", requestBody, "id=1", new ServiceOptions());

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

        Response response = service.patch("test", requestBody, "id=1", new ServiceOptions());

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

        Response response = service.delete("test", "", "id=1", new ServiceOptions());

        assertEquals(response.code(), 200);
        assertNotNull(response.body());
        assertEquals(response.body().string(), responseBody);
        verify(deleteRequestedFor(urlPathEqualTo(url))
                .withQueryParam("id", WireMock.equalTo("1")));
    }
}
