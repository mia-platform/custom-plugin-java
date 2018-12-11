package eu.miaplatform.customplugin;

import java.util.ArrayList;
import java.util.List;

public class CustomPluginHeadersPropagatorImpl implements CustomPluginHeadersPropagator {

    private List<CustomPluginHeader> customPluginHeaders = new ArrayList<>();

    @Override
    public void add(String headerName, String headerValue) {
        customPluginHeaders.add(new CustomPluginHeaderImpl(headerName, headerValue));
    }

    @Override
    public List<CustomPluginHeader> getHeaders() {
        return customPluginHeaders;
    }
}
