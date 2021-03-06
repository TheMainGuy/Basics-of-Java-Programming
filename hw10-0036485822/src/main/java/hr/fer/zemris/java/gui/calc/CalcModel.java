package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleBinaryOperator;

/**
 * Defines calculator model used to implement calculator. Model stores variables
 * and has methods needed for calculator to work.
 * 
 * @author tin
 *
 */
public interface CalcModel {
  /**
   * Adds {@link CalcValueListener} which will be notified upon all changes in
   * this {@link CalcModel} object.
   * 
   * @param l listener to be added
   */
  void addCalcValueListener(CalcValueListener l);

  /**
   * Removes {@link CalcValueListener} from list of added listeners.
   * 
   * @param l listener to be removed
   */
  void removeCalcValueListener(CalcValueListener l);

  /**
   * Converts current number to string.
   * 
   * @return current number converted to string
   */
  String toString();

  /**
   * Returns current number.
   * 
   * @return current number
   */
  double getValue();

  /**
   * Sets current number value.
   * 
   * @param value value to be set
   */
  void setValue(double value);

  /**
   * Clears current number value.
   */
  void clear();

  /**
   * Clears current number, current operand and current operator values.
   */
  void clearAll();

  /**
   * Negates current number. If there is no current number, does nothing.
   */
  void swapSign();

  /**
   * Inserts decimal point. If decimal point already exist, does nothing.
   */
  void insertDecimalPoint();

  /**
   * Appends digit to current number. If number is already 0 without decimal
   * point, does nothing.
   * 
   * @param digit digit to be inserted
   */
  void insertDigit(int digit);

  /**
   * Checks if there is active operand stored and returns <code>true</code> if it
   * is stored, <code>false</code> if it is not.
   * 
   * @return <code>true</code> if active operand is set, <code>false</code> if it
   *         is not.
   */
  boolean isActiveOperandSet();

  /**
   * Returns active operand. Throws exception if there is no active operand.
   * 
   * @return active operand
   * @throws IllegalStateException if there is no active operand
   */
  double getActiveOperand();

  /**
   * Sets active operand to activeOperand parameter.
   * 
   * @param activeOperand value to which active operand will be set
   */
  void setActiveOperand(double activeOperand);

  /**
   * Clears active operand.
   */
  void clearActiveOperand();

  /**
   * Returns pending binary operation.
   * 
   * @return pending binary operation
   */
  DoubleBinaryOperator getPendingBinaryOperation();

  /**
   * Sets pending binary operation to given binary operation.
   * 
   * @param op operation to which binaryOperation will be set
   */
  void setPendingBinaryOperation(DoubleBinaryOperator op);
}