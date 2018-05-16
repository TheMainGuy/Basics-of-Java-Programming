package hr.fer.zemris.java.gui.calc.elements;

import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.CalcModel;

public class CalcReverseButton extends JButton {
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
   * @param binaryOperator operation which pressing this button will execute
   * @param reversedBinaryOperator operation which pressing this button will
   *          execute when reversed
   * @param calculator {@link CalcModel} object in which result is stored
   */
  public CalcReverseButton(String buttonText, String reversedButtonText, DoubleBinaryOperator binaryOperator,
      DoubleBinaryOperator reversedBinaryOperator, CalcModel calculator) {
    super(buttonText);
    isReversed = false;
    this.buttonText = buttonText;
    this.reversedButtonText = reversedButtonText;

    this.addActionListener(a -> {
      if(calculator.getPendingBinaryOperation() != null && calculator.isActiveOperandSet()) {
        calculator.setActiveOperand(
            calculator.getPendingBinaryOperation().applyAsDouble(calculator.getActiveOperand(), calculator.getValue()));
      } else {
        calculator.setActiveOperand(calculator.getValue());
      }
      DoubleBinaryOperator x = isReversed ? reversedBinaryOperator : binaryOperator;
      calculator.setPendingBinaryOperation(x);
      calculator.clear();
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
