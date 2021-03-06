package hr.fer.zemris.java.gui.layouts;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Used for testing {@link CalcLayout} class.
 * 
 * @author tin
 *
 */
public class CalcLayoutDemo {
  /**
   * Method which is called when program starts.
   * 
   * @param args command line arguments
   */
  public static void main(String[] args) {
    JPanel p = new JPanel(new CalcLayout(3));
    p.add(new JLabel("x"), new RCPosition(1, 1));
    p.add(new JLabel("y"), new RCPosition(2, 3));
    p.add(new JLabel("z"), new RCPosition(2, 7));
    p.add(new JLabel("w"), new RCPosition(4, 2));
    p.add(new JLabel("a"), new RCPosition(4, 5));
    p.add(new JLabel("b"), new RCPosition(4, 7));
    p.setVisible(true);
  }
}
