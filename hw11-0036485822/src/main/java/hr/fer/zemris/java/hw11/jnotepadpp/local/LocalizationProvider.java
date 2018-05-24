package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Singleton class which gets localized names for elements whose names are
 * locale changable.
 * 
 * @author tin
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

  /**
   * Defines translations files location.
   */
  private final String TRANSLATIONS_LOCATION = "hr.fer.zemris.java.hw11.jnotepadpp.local.translations";

  /**
   * Stores current language.
   */
  private String language = "en";

  /**
   * Stores resource bundle reference.
   */
  private ResourceBundle bundle;

  /**
   * The only instance of {@link LocalizationProvider}.
   */
  private static LocalizationProvider instance = new LocalizationProvider();

  /**
   * Constructor.
   */
  public LocalizationProvider() {
    Locale locale = Locale.forLanguageTag(language);
    bundle = ResourceBundle.getBundle(TRANSLATIONS_LOCATION, locale);
  }

  /**
   * Returns the instance of {@link LocalizationProvider}.
   * 
   * @return instance of {@link LocalizationProvider}
   */
  public static LocalizationProvider getInstance() {
    return instance;
  }

  @Override
  public String getString(String s) {
    return bundle.getString(s);
  }

  /**
   * Sets current language to given language.
   * 
   * @param language language to which the current language will be set
   */
  public void setLanguage(String language) {
    if(!this.language.equals(language)) {
      this.language = language;
      Locale locale = Locale.forLanguageTag(language);
      bundle = ResourceBundle.getBundle(TRANSLATIONS_LOCATION, locale);
      fire();
    } else {
      this.language = language;
    }
  }

}
