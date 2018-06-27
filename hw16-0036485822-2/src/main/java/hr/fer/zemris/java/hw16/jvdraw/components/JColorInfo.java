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

  private IColorProvider colorArea1;

  private IColorProvider colorArea2;

  public JColorInfo(JColorArea colorArea1, JColorArea colorArea2) {
    this.colorArea1 = colorArea1;
    this.colorArea2 = colorArea2;
    colorArea1.addColorChangeListener(this);
    colorArea2.addColorChangeListener(this);
    updateInfo();
  }

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
