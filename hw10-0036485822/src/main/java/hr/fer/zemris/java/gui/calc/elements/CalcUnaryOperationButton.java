package hr.fer.zemris.java.gui.calc.elements;

import java.util.function.DoubleUnaryOperator;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.CalcModel;

/**
 * Implements unary operation button which, when pressed, executes unary
 * operation defined.
 * 
 * @author tin
 *
 */
public class CalcUnaryOperationButton extends JButton {
  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Set to <code>true</code> if button operation is reversed, <code>false</code>
   * if it is not.
   */
  private boolean isReversed;

  /**
   * String which is displayed on button when not reversed.
   */
  private String buttonText;

  /**
   * String which is displayed on button when reversed.
   */
  private String reversedButtonText;

  /**
   * Constructor.
   * 
   * @param buttonText button text which is displayed
   * @param reversedButtonText button text which is displayed when reversed
   * @param unaryOperator operation which pressing this button will execute
   * @param reversedUnaryOperator operation which pressing this button will
   *          execute when reversed
   * @param calculator {@link CalcModel} object in which result is stored
   */
  public CalcUnaryOperationButton(String buttonText, String reversedButtonText, DoubleUnaryOperator unaryOperator,
      DoubleUnaryOperator reversedUnaryOperator, CalcModel calculator) {
    super(buttonText);
    isReversed = false;
    this.buttonText = buttonText;
    this.reversedButtonText = reversedButtonText;
    this.addActionListener(a -> {
      calculator.setValue(isReversed ? reversedUnaryOperator.applyAsDouble(calculator.getValue())
          : unaryOperator.applyAsDouble(calculator.getValue()));
    });
  }

  /**
   * Reverses this button text and function.
   */
  public void reverse() {
    isReversed = isReversed ? false : true;
    setText(isReversed ? reversedButtonText : buttonText);
  }

}
