package eu.miaplatform.customplugin;

final class CustomPluginHeaderImpl implements CustomPluginHeader {

    private String name;
    private String value;

    public CustomPluginHeaderImpl(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
