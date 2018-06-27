package hr.fer.zemris.java.hw16.jvdraw.listeners;

import java.awt.Color;

/**
 * Defines listener which can listen to {@link IColorProvider} objects for color
 * change. Expects to be notified on each color change.
 * 
 * @author tin
 *
 */
public interface ColorChangeListener {
  /**
   * Method which is called when color of the listened object changes.
   * 
   * @param source object which is listened to
   * @param oldColor old color
   * @param newColor new color
   */
  public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}
