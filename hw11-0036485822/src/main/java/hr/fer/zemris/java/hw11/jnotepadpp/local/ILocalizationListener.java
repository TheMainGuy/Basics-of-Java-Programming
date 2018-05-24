package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Defines localization listener whose method localizationChanged is called
 * whenever language is changed within object this listener is attached to.
 * 
 * @author tin
 *
 */
public interface ILocalizationListener {
  /**
   * Method which is called when language is changed within object this listener
   * is attached to.
   */
  void localizationChanged();
}
