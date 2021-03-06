package hr.fer.zemris.java.gui.calc.elements;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.CalcModel;

/**
 * Implements number button which, when pressed, calls calculator's insertDigit
 * method.
 * 
 * @author tin
 *
 */
public class CalcNumberButton extends JButton {
  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Constructor.
   * 
   * @param buttonDigit digit which this button will show
   * @param calculator {@link CalcModel} which saves digits
   */
  public CalcNumberButton(int buttonDigit, CalcModel calculator) {
    super(Integer.toString(buttonDigit));
    this.addActionListener(a -> {
      calculator.insertDigit(buttonDigit);
    });
  }

}
