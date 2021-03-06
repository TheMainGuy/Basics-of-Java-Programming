package hr.fer.zemris.java.gui.calc.elements;

import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.CalcModel;

/**
 * Implements binary operation button which, when pressed, executes binary
 * operation using operands and operator stored in given {@link CalcModel}
 * object.
 * 
 * @author tin
 *
 */
public class CalcBinaryOperationButton extends JButton {

  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Constructor.
   * 
   * @param buttonText button text which is displayed
   * @param unaryOperator operation which pressing this button will execute
   * @param calculator {@link CalcModel} object in which result is stored
   */
  public CalcBinaryOperationButton(String buttonText, DoubleBinaryOperator binaryOperator, CalcModel calculator) {
    super(buttonText);
    
    this.addActionListener(a -> {
      if(calculator.getPendingBinaryOperation() != null && calculator.isActiveOperandSet()) {
        calculator.setActiveOperand(
            calculator.getPendingBinaryOperation().applyAsDouble(calculator.getActiveOperand(), calculator.getValue()));
      }
      else {
        calculator.setActiveOperand(calculator.getValue());
      }
      calculator.setPendingBinaryOperation(binaryOperator);
      calculator.clear();
    });
  }
}
