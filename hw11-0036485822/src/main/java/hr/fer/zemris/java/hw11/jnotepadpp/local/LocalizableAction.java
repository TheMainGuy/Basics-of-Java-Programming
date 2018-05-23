package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.AbstractAction;

public abstract class LocalizableAction extends AbstractAction {

  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;
  
  public LocalizableAction(String key, ILocalizationProvider localizationProvider) {
    this.putValue(key, localizationProvider.getString(key));
    localizationProvider.addLocalizationListener(new ILocalizationListener() {
      
      @Override
      public void localizationChanged() {
        putValue(key, localizationProvider.getString(key));
      }
    });
    
  }
}
