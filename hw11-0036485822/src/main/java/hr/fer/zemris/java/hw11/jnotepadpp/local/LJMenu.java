package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.JMenu;

public class LJMenu extends JMenu {

  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;
  
  private String key;
  
  private ILocalizationProvider localizationProvider;
  
  public LJMenu(String key, ILocalizationProvider localizationProvider) {
    super();
    this.key = key;
    this.localizationProvider = localizationProvider;
    localizationProvider.addLocalizationListener(new ILocalizationListener() {
      
      @Override
      public void localizationChanged() {
        updateMenu();
      }
    });
    setMenuText();
  }
  
  private void updateMenu() {
    setText(localizationProvider.getString(key));
  }
  
  private void setMenuText() {
    setText(localizationProvider.getString(key));
  }
}