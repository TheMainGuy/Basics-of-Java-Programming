package hr.fer.zemris.java.hw16.jvdraw.components;

import java.awt.Color;

import javax.swing.JLabel;

import hr.fer.zemris.java.hw16.jvdraw.listeners.ColorChangeListener;
import hr.fer.zemris.java.hw16.jvdraw.listeners.IColorProvider;

/**
 * Status bar for JVDraw which shows currently selected foreground and
 * background colors.
 * 
 * @author tin
 *
 */
public class JColorInfo extends JLabel implements ColorChangeListener {

  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Reference to first color provider.
   */
  private IColorProvider colorArea1;

  /**
   * Reference to second color provider.
   */
  private IColorProvider colorArea2;

  /**
   * Constructor.
   * 
   * @param colorArea1 reference to first color provider
   * @param colorArea2 reference to second color provider
   */
  public JColorInfo(JColorArea colorArea1, JColorArea colorArea2) {
    this.colorArea1 = colorArea1;
    this.colorArea2 = colorArea2;
    colorArea1.addColorChangeListener(this);
    colorArea2.addColorChangeListener(this);
    updateInfo();
  }

  /**
   * Helper method which updates this object's text using information from
   * {@link IColorProvider} objects.
   */
  private void updateInfo() {
    Color foregroundColor = colorArea1.getCurrentColor();
    Color backgroundColor = colorArea2.getCurrentColor();
    setText("Foreground color: (" + foregroundColor.getRed() + ", " + foregroundColor.getGreen() + ", "
        + foregroundColor.getBlue() + "), background color: (" + backgroundColor.getRed() + ", "
        + backgroundColor.getGreen() + ", " + backgroundColor.getBlue() + ").");

  }

  @Override
  public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
    updateInfo();
  }

}
