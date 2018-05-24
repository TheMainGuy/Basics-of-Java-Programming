package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements {@link ILocalizationProvider} methods addLocalizationListener and
 * removeLocalizationListener with another method fire which notifies all
 * listeners.
 * 
 * @author tin
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {
  /**
   * List of all listeners listening to this object.
   */
  private List<ILocalizationListener> listeners = new ArrayList<>();
  
  @Override
  public void addLocalizationListener(ILocalizationListener listener) {
    listeners.add(listener);
  }

  @Override
  public void removeLocalizationListener(ILocalizationListener listener) {
    listeners.remove(listener);
  }

  /**
   * Notifies all listeners attached to this object.
   */
  public void fire() {
    for (ILocalizationListener listener : listeners) {
      listener.localizationChanged();
    }
  }
}
