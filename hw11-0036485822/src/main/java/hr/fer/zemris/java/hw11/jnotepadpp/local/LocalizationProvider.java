package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationProvider extends AbstractLocalizationProvider {
  
  private final String TRANSLATIONS_LOCATION = "hr.fer.zemris.java.hw11.jnotepadpp.local.translations";
  private String language = "en";
  private ResourceBundle bundle;
  private static LocalizationProvider instance = new LocalizationProvider();

  public LocalizationProvider() {
    Locale locale = Locale.forLanguageTag(language);
    bundle = ResourceBundle.getBundle(TRANSLATIONS_LOCATION, locale);
  }

  public static LocalizationProvider getInstance() {
    return instance;
  }

  @Override
  public String getString(String s) {
    return bundle.getString(s);
  }

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
