package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.JLabel;

public class LJLabel extends JLabel {

  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;
  
  private String key;
  
  private ILocalizationProvider localizationProvider;
  
  public LJLabel(String key, ILocalizationProvider localizationProvider) {
    super();
    this.key = key;
    this.localizationProvider = localizationProvider;
    localizationProvider.addLocalizationListener(new ILocalizationListener() {
      
      @Override
      public void localizationChanged() {
        updateLabel();
      }
    });
  }
  
  private void updateLabel() {
    setText(localizationProvider.getString(key));
  }
}
