package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Defines possible operation types for methods in {@link ValueWrapper}.
 * 
 * @author tin
 *
 */
public enum OperationType {
  /**
   * Represents double number operation used in {@link ValueWrapper}. Double
   * operation always results in {@link Double} value.
   */
  DOUBLE, 
  
  /**
   * Represents integer number operation used in {@link ValueWrapper}. Integer
   * operation always results in {@link Integer} value.
   */
  INTEGER
}
