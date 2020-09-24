package eu.miaplatform.service;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import okhttp3.Response;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ServiceTest {
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(3000);

    private Service service;
    private final String path = "/test";

    @Before
    public void setup() {
        service = new Service("localhost", new InitServiceOptions());
    }

    @Test
    public void testGet() throws IOException {
        String responseBody = "{\"result\":\"ok\"}";
        stubFor(WireMock.get(urlPathEqualTo(path))
                .withQueryParam("id", WireMock.equalTo("1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(responseBody)));

        Response response = service.get(path, "id=1", new ServiceOptions());

        assertEquals(response.code(), 200);
        assertNotNull(response.body());
        assertEquals(response.body().string(), responseBody);
        verify(getRequestedFor(urlPathEqualTo(path))
                .withQueryParam("id", WireMock.equalTo("1")));
    }

    @Test
    public void testGetNullOptions() throws IOException {
        String responseBody = "{\"result\":\"ok\"}";
        stubFor(WireMock.get(urlPathEqualTo(path))
                .withQueryParam("id", WireMock.equalTo("1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(responseBody)));

        Response response = service.get(path, "id=1", null);

        assertEquals(response.code(), 200);
        assertNotNull(response.body());
        assertEquals(response.body().string(), responseBody);
        verify(getRequestedFor(urlPathEqualTo(path))
                .withQueryParam("id", WireMock.equalTo("1")));
    }

    @Test
    public void testGetNullQuery() throws IOException {
        String responseBody = "{\"result\":\"ok\"}";
        stubFor(WireMock.get(urlPathEqualTo(path))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(responseBody)));

        Response response = service.get(path, null, new ServiceOptions());

        assertEquals(response.code(), 200);
        assertNotNull(response.body());
        assertEquals(response.body().string(), responseBody);
        verify(getRequestedFor(urlPathEqualTo(path)));
    }

    @Test
    public void testGetNullQueryAndOptions() throws IOException {
        String responseBody = "{\"result\":\"ok\"}";
        stubFor(WireMock.get(urlPathEqualTo(path))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(responseBody)));

        Response response = service.get(path, null, null);

        assertEquals(response.code(), 200);
        assertNotNull(response.body());
        assertEquals(response.body().string(), responseBody);
        verify(getRequestedFor(urlPathEqualTo(path)));
    }

    @Test
    public void testPost() throws IOException {
        String requestBody = "{\"foo\":\"bar\"}";
        stubFor(WireMock.post(urlPathEqualTo(path))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(requestBody)));

        Response response = service.post(path, requestBody, "", new ServiceOptions());

        assertEquals(response.code(), 200);
        assertNotNull(response.body());
        assertEquals(response.body().string(), requestBody);
        verify(postRequestedFor(urlPathEqualTo(path)));
    }

    @Test
    public void testPostNullQuery() throws IOException {
        String requestBody = "{\"foo\":\"bar\"}";
        stubFor(WireMock.post(urlPathEqualTo(path))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(requestBody)));

        Response response = service.post(path, requestBody, null, new ServiceOptions());

        assertEquals(response.code(), 200);
        assertNotNull(response.body());
        assertEquals(response.body().string(), requestBody);
        verify(postRequestedFor(urlPathEqualTo(path)));
    }

    @Test
    public void testPut() throws IOException {
        String requestBody = "{\"foo\":\"bar\"}";
        stubFor(WireMock.put(urlPathEqualTo(path))
                .withQueryParam("id", WireMock.equalTo("1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(requestBody)));

        Response response = service.put(path, requestBody, "id=1", new ServiceOptions());

        assertEquals(response.code(), 200);
        assertNotNull(response.body());
        assertEquals(response.body().string(), requestBody);
        verify(putRequestedFor(urlPathEqualTo(path))
                .withQueryParam("id", WireMock.equalTo("1")));
    }

    @Test
    public void testPutNullOptions() throws IOException {
        String requestBody = "{\"foo\":\"bar\"}";
        stubFor(WireMock.put(urlPathEqualTo(path))
                .withQueryParam("id", WireMock.equalTo("1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(requestBody)));

        Response response = service.put(path, requestBody, "id=1", null);

        assertEquals(response.code(), 200);
        assertNotNull(response.body());
        assertEquals(response.body().string(), requestBody);
        verify(putRequestedFor(urlPathEqualTo(path))
                .withQueryParam("id", WireMock.equalTo("1")));
    }

    @Test
    public void testPutNullQuery() throws IOException {
        String requestBody = "{\"foo\":\"bar\"}";
        stubFor(WireMock.put(urlPathEqualTo(path))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(requestBody)));

        Response response = service.put(path, requestBody, null, new ServiceOptions());

        assertEquals(response.code(), 200);
        assertNotNull(response.body());
        assertEquals(response.body().string(), requestBody);
        verify(putRequestedFor(urlPathEqualTo(path)));
    }

    @Test
    public void testPatch() throws IOException {
        String requestBody = "{\"$set\":{\"count\":42}}";
        stubFor(WireMock.patch(urlPathEqualTo(path))
                .withQueryParam("id", WireMock.equalTo("1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(requestBody)));

        Response response = service.patch(path, requestBody, "id=1", new ServiceOptions());

        assertEquals(response.code(), 200);
        assertNotNull(response.body());
        assertEquals(response.body().string(), requestBody);
        verify(patchRequestedFor(urlPathEqualTo(path))
                .withQueryParam("id", WireMock.equalTo("1")));
    }

    @Test
    public void testPatchNullQueryAndOptions() throws IOException {
        String requestBody = "{\"$set\":{\"count\":42}}";
        stubFor(WireMock.patch(urlPathEqualTo(path))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(requestBody)));

        Response response = service.patch(path, requestBody, null, null);

        assertEquals(response.code(), 200);
        assertNotNull(response.body());
        assertEquals(response.body().string(), requestBody);
        verify(patchRequestedFor(urlPathEqualTo(path)));
    }

    @Test
    public void testDelete() throws IOException {
        String responseBody = "{\"result\":\"ok\"}";

        stubFor(WireMock.delete(urlPathEqualTo(path))
                .withQueryParam("id", WireMock.equalTo("1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(responseBody)));

        Response response = service.delete(path, "", "id=1", new ServiceOptions());

        assertEquals(response.code(), 200);
        assertNotNull(response.body());
        assertEquals(response.body().string(), responseBody);
        verify(deleteRequestedFor(urlPathEqualTo(path))
                .withQueryParam("id", WireMock.equalTo("1")));
    }

    @Test
    public void testDeleteNullQuery() throws IOException {
        String responseBody = "{\"result\":\"ok\"}";

        stubFor(WireMock.delete(urlPathEqualTo(path))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(responseBody)));

        Response response = service.delete(path, "", null, new ServiceOptions());

        assertEquals(response.code(), 200);
        assertNotNull(response.body());
        assertEquals(response.body().string(), responseBody);
        verify(deleteRequestedFor(urlPathEqualTo(path)));
    }

    @Test
    public void testDeleteNullOptions() throws IOException {
        String responseBody = "{\"result\":\"ok\"}";

        stubFor(WireMock.delete(urlPathEqualTo(path))
                .withQueryParam("id", WireMock.equalTo("1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(responseBody)));

        Response response = service.delete(path, "", "id=1", null);

        assertEquals(response.code(), 200);
        assertNotNull(response.body());
        assertEquals(response.body().string(), responseBody);
        verify(deleteRequestedFor(urlPathEqualTo(path))
                .withQueryParam("id", WireMock.equalTo("1")));
    }

    @Test
    public void testSubRoutes() throws IOException {
        String responseBody = "{\"result\":\"ok\"}";
        String pathWithSubRoutes = "/sub/route/test";
        stubFor(WireMock.get(urlPathEqualTo(pathWithSubRoutes))
                .withQueryParam("id", WireMock.equalTo("1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(responseBody)));

        Response response = service.get(pathWithSubRoutes, "id=1", new ServiceOptions());

        assertEquals(response.code(), 200);
        assertNotNull(response.body());
        assertEquals(response.body().string(), responseBody);
        verify(getRequestedFor(urlPathEqualTo(pathWithSubRoutes))
                .withQueryParam("id", WireMock.equalTo("1")));
    }
}
