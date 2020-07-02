package eu.miaplatform.customplugin;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import eu.miaplatform.service.*;
import okhttp3.Headers;
import okhttp3.Response;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class HTTPServiceClientTest {
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
}
