package eu.miaplatform.customplugin;

public class HeadersPropagatorFactory {
  public static CustomPluginHeadersPropagator getCustomPluginHeadersPropagator() {
    return new CustomPluginHeadersPropagatorImpl();
  }
}
