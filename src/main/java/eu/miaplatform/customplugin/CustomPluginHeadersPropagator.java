package eu.miaplatform.customplugin;

import java.util.List;

public interface CustomPluginHeadersPropagator {

    void add(String headerName, String headerValue);
    List<CustomPluginHeader> getHeaders();
}
