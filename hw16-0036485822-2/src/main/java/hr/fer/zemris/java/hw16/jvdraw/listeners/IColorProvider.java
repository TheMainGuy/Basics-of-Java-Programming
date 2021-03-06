package hr.fer.zemris.java.hw16.jvdraw.listeners;

import java.awt.Color;

/**
 * Defines interface for objects which have some color property. Allows
 * {@link ColorChangeListener}s to be added and removed.
 * 
 * @author tin
 *
 */
public interface IColorProvider {
  /**
   * Returns color.
   * 
   * @return color
   */
  public Color getCurrentColor();

  /**
   * Adds new listener to list of listeners. Each listener should be notified when
   * color property changes.
   * 
   * @param l listener to be added
   */
  public void addColorChangeListener(ColorChangeListener l);

  /**
   * Removes listener from list of listeners.
   * 
   * @param l listener to be removed
   */
  public void removeColorChangeListener(ColorChangeListener l);
}
