package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Defines methods localization provider must implement.
 * 
 * @author tin
 *
 */
public interface ILocalizationProvider {
  /**
   * Adds {@link ILocalizationListener} to this object.
   * 
   * @param listener listener which will be added to this object
   */
  void addLocalizationListener(ILocalizationListener listener);

  /**
   * Removes listener from this object.
   * 
   * @param listener listener which will be removed from this object
   */
  void removeLocalizationListener(ILocalizationListener listener);

  /**
   * Returns localized string for given key.
   * 
   * @param s key
   * @return localized string assigned to given key
   */
  String getString(String s);
}
