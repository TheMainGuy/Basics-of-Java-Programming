package hr.fer.zemris.java.hw11.jnotepadpp.local.ljelements;

import javax.swing.JMenu;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Defines localizable menu which puts listener on given
 * {@link ILocalizationProvider} object. Upon change of localization,
 * localizationChanged method will be called and change menu's text.
 * 
 * @author tin
 *
 */
public class LJMenu extends JMenu {

  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;
  
  /**
   * Key used to get localized translation.
   */
  private String key;
  
  /**
   * Reference to {@link ILocalizationProvider} object which is used to get localization settings.
   */
  private ILocalizationProvider localizationProvider;
  
  /**
   * Constructor.
   * 
   * @param key key value for getting localized word.
   * @param localizationProvider localization provider from which will be used to get localization settings
   */
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
    updateMenu();
  }
  
  /**
   * Updates menu text.
   */
  private void updateMenu() {
    setText(localizationProvider.getString(key));
  }
}