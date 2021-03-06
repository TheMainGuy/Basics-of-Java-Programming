package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

/**
 * Implements calculator model. Model stores current number which is displayed
 * on the calculator screen, current operand which is previous number stored
 * after user pressed operator button and current operation which is stored when
 * user presses the operator button.
 * 
 * @author tin
 *
 */
public class CalcModelImpl implements CalcModel {
  /**
   * Current number displayed.
   */
  private String currentNumber = null;
  /**
   * Stored number.
   */
  private double activeOperand = Double.NaN;
  /**
   * Stored binary operator.
   */
  private DoubleBinaryOperator binaryOperator = null;
  /**
   * Value listeners.
   */
  private List<CalcValueListener> valueListeners = new ArrayList<>();

  @Override
  public void addCalcValueListener(CalcValueListener l) {
    valueListeners.add(l);
  }

  @Override
  public void removeCalcValueListener(CalcValueListener l) {
    valueListeners.remove(l);
  }

  @Override
  public String toString() {
    return currentNumber == null || currentNumber.equals("") ? "0" : currentNumber;
  }

  @Override
  public double getValue() {
    return currentNumber == null || currentNumber.equals("") ? 0.0 : Double.parseDouble(currentNumber);
  }

  @Override
  public void setValue(double value) {
    currentNumber = (value == Double.NaN || value == Double.POSITIVE_INFINITY || value == Double.NEGATIVE_INFINITY)
        ? currentNumber
        : Double.toString(value);
    removeDotZero();
    notifyValueListeners();
  }

  @Override
  public void clear() {
    currentNumber = null;
    notifyValueListeners();
  }

  @Override
  public void clearAll() {
    currentNumber = null;
    activeOperand = Double.NaN;
    binaryOperator = null;
    notifyValueListeners();
  }

  @Override
  public void swapSign() {
    currentNumber = currentNumber == null ? currentNumber : Double.toString(Double.parseDouble(currentNumber) * -1);
    removeDotZero();
    notifyValueListeners();
  }

  @Override
  public void insertDecimalPoint() {
    if(currentNumber != null && currentNumber.length() >= 308) {
      return;
    }
    if(currentNumber == null || currentNumber.equals("")) {
      currentNumber = "0.";
      notifyValueListeners();
      return;
    }
    currentNumber = currentNumber.indexOf('.') == -1 ? currentNumber + '.' : currentNumber;
    notifyValueListeners();
  }

  @Override
  public void insertDigit(int digit) {
    if(currentNumber != null && currentNumber.length() >= 308) {
      return;
    }
    if(currentNumber == null) {
      currentNumber = "";
    }
    if(digit == 0 && currentNumber.equals("0")) {
      return;
    }
    if(currentNumber.equals("0") && digit != 0) {
      currentNumber = Integer.toString(digit);
      notifyValueListeners();
      return;
    }
    currentNumber += digit;
    notifyValueListeners();

  }

  @Override
  public boolean isActiveOperandSet() {
    return Double.isNaN(activeOperand) ? false : true;
  }

  @Override
  public double getActiveOperand() {
    if(isActiveOperandSet()) {
      return activeOperand;
    }
    throw new IllegalStateException("Active operand is not set.");
  }

  @Override
  public void setActiveOperand(double activeOperand) {
    this.activeOperand = activeOperand;
    notifyValueListeners();
  }

  @Override
  public void clearActiveOperand() {
    activeOperand = Double.NaN;
    notifyValueListeners();
  }

  @Override
  public DoubleBinaryOperator getPendingBinaryOperation() {
    return binaryOperator;
  }

  @Override
  public void setPendingBinaryOperation(DoubleBinaryOperator op) {
    binaryOperator = op;
    notifyValueListeners();
  }

  /**
   * Method calls value changed method on each listener attached to this object.
   */
  private void notifyValueListeners() {
    for (CalcValueListener valueListener : valueListeners) {
      valueListener.valueChanged(this);
    }
  }

  /**
   * Helper method which removes .0 when called.
   */
  private void removeDotZero() {
    if(currentNumber == null || currentNumber.indexOf('.') == -1) {
      return;
    }
    if(Double.parseDouble(currentNumber) == Math.floor(Double.parseDouble(currentNumber))) {
      currentNumber = currentNumber.substring(0, currentNumber.indexOf('.'));
    }
  }

}
