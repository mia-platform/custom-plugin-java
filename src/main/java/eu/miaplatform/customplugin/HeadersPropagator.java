package eu.miaplatform.customplugin;

import java.util.List;

public interface HeadersPropagator {

    void add(String headerName, String headerValue);
    List<CustomPluginHeader> getHeaders ();
}
