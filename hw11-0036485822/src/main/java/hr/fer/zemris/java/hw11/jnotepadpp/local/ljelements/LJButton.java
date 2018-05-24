package hr.fer.zemris.java.hw11.jnotepadpp.local.ljelements;

import javax.swing.Action;
import javax.swing.JButton;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Defines localizable button which puts listener on given
 * {@link ILocalizationProvider} object. Upon change of localization,
 * localizationChanged method will be called and change button's text.
 * 
 * @author tin
 *
 */
public class LJButton extends JButton {

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
   * @param action action which is performed by pressing the button
   */
  public LJButton(String key, ILocalizationProvider localizationProvider, Action action) {
    super(action);
    this.key = key;
    this.localizationProvider = localizationProvider;
    localizationProvider.addLocalizationListener(new ILocalizationListener() {
      
      @Override
      public void localizationChanged() {
        updateButton();
      }
    });
    updateButton();
  }
  
  /**
   * Updates button text.
   */
  private void updateButton() {
    setText(localizationProvider.getString(key));
  }
}