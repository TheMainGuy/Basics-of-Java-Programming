package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.AbstractAction;

/**
 * Defines localizable action which puts listener on given
 * {@link ILocalizationProvider} object. Upon change of localization,
 * localizationChanged method will be called.
 * 
 * @author tin
 *
 */
public abstract class LocalizableAction extends AbstractAction {

  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Constructor.
   * 
   * @param key key value for getting localized word.
   * @param localizationProvider localization provider from which localized words are get
   */
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
