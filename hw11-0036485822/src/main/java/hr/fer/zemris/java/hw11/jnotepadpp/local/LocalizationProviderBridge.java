package hr.fer.zemris.java.hw11.jnotepadpp.local;

public class LocalizationProviderBridge extends AbstractLocalizationProvider {
  private boolean connected;
  private ILocalizationProvider localizationProvider;
  private ILocalizationListener localizationListener;
  
  public LocalizationProviderBridge(ILocalizationProvider localizationProvider) {
    this.localizationProvider = localizationProvider;
    localizationListener = new ILocalizationListener() {
      
      @Override
      public void localizationChanged() {
        fire();
      }
    };
  }
  
  public void connect() {
    if(connected == true) {
      return;
    }
    localizationProvider.addLocalizationListener(localizationListener);
  }
  
  public void disconnect() {
    if(connected == false) {
      return;
    }
    localizationProvider.removeLocalizationListener(localizationListener);
  }
  
  @Override
  public String getString(String s) {
    return localizationProvider.getString(s);
  }

}
