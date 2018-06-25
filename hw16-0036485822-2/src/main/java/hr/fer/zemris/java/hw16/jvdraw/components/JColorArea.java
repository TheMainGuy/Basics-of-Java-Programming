package hr.fer.zemris.java.hw16.jvdraw.components;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JComponent;

/**
 * 
 * @author tin
 *
 */
public class JColorArea extends JComponent {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Color which this color area will display
   */
  private Color selectedColor;

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(15, 15);
  }

  /**
   * Constructor.
   * 
   * @param selectedColor selected color
   */
  public JColorArea(Color selectedColor) {
    super();
    this.selectedColor = selectedColor;
  }

  /**
   * @return the selectedColor
   */
  public Color getSelectedColor() {
    return selectedColor;
  }

  /**
   * @param selectedColor the selectedColor to set
   */
  public void setSelectedColor(Color selectedColor) {
    this.selectedColor = selectedColor;
  }

}
