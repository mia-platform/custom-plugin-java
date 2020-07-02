package eu.miaplatform.customplugin;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import eu.miaplatform.service.*;
import okhttp3.Headers;
import org.junit.Test;

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
}
