package hr.fer.zemris.java.hw16.jvdraw.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.listeners.ColorChangeListener;
import hr.fer.zemris.java.hw16.jvdraw.listeners.IColorProvider;

/**
 * Implements small color area which can be clicked on to change its color. When
 * clicked, opens {@link JColorChooser} to allow user to choose color.
 * 
 * @author tin
 *
 */
public class JColorArea extends JComponent implements IColorProvider {

  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Color which this color area will display
   */
  private Color selectedColor;

  /**
   * List of all listeners listening to this object.
   */
  private List<ColorChangeListener> listeners = new ArrayList<>();

  /**
   * Constructor.
   * 
   * @param selectedColor selected color
   */
  public JColorArea(Color selectedColor) {
    super();
    this.selectedColor = selectedColor;

    this.addMouseListener(new MouseListener() {

      @Override
      public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub

      }

      @Override
      public void mousePressed(MouseEvent arg0) {
        // TODO Auto-generated method stub

      }

      @Override
      public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub

      }

      @Override
      public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub

      }

      @Override
      public void mouseClicked(MouseEvent arg0) {
        // JColorChooser colorChooser = new JColorChooser(selectedColor);
        Color newColor = JColorChooser.showDialog(null, "Choose color", getSelectedColor());
        if(newColor != null) {
          setSelectedColor(newColor);
        }
      }
    });
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(15, 15);
  }

  @Override
  public Dimension getMaximumSize() {
    return new Dimension(15, 50);
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
    Color oldColor = this.selectedColor;
    this.selectedColor = selectedColor;
    repaint();
    notifyListeners(oldColor);

  }

  /**
   * Notifies all listeners currently listening to this object about color change.
   * 
   * @param oldColor color before change
   */
  private void notifyListeners(Color oldColor) {
    for (ColorChangeListener listener : listeners) {
      listener.newColorSelected(this, oldColor, selectedColor);
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    g2.setColor(getSelectedColor());
    g2.fillRect(0, 0, 15, 15);
  }

  @Override
  public Color getCurrentColor() {
    return selectedColor;
  }

  @Override
  public void addColorChangeListener(ColorChangeListener l) {
    if(l == null) {
      throw new NullPointerException();
    }
    listeners.add(l);
  }

  @Override
  public void removeColorChangeListener(ColorChangeListener l) {
    listeners.remove(l);
  }
}
