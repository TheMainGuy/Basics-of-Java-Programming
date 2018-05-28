package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Simple data wrapper primarily for wrapping numbers in {@link Integer},
 * {@link Double}, {@link String} and <code>null</code> format. Implemenets
 * basic add, subtract, multiply, divide and number comparing operations.
 * Performing any of these operations results in {@link Integer} operation, but
 * operating on at least 1 {@link Double} results in {@link Double} operation.
 * 
 * @author tin
 *
 */
public class ValueWrapper {
  /**
   * Stored value
   */
  private Object value;

  /**
   * Constructor.
   * 
   * @param value value
   */
  public ValueWrapper(Object value) {
    this.value = value;
  }

  /**
   * Returns object value.
   * 
   * @return object value
   */
  public Object getValue() {
    return value;
  }

  /**
   * Sets value to given value.
   * 
   * @param value value to be set
   */
  public void setValue(Object value) {
    this.value = value;
  }

  /**
   * Adds given value to value stored in this object. If both objects are
   * {@link Integer}s, performs {@link Integer} operation. If both objects are
   * {@link Double}, performs {@link Double} operation.
   * 
   * @param incValue value to be added to this object's value
   */
  public void add(Object incValue) {
    Object number1 = parseNumber(value);
    Object number2 = parseNumber(incValue);
    OperationType operationType = getOperationType(number1, number2);
    if(operationType == OperationType.INTEGER) {
      value = (Integer) number1 + (Integer) number2;
    }
    else {
      value = ((Number) number1).doubleValue() + ((Number) number2).doubleValue();
    }
  }

  /**
   * Subtracts given value from value stored in this object. If both objects are
   * {@link Integer}s, performs {@link Integer} operation. If both objects are
   * {@link Double}, performs {@link Double} operation.
   * 
   * @param decValue value to be subtracted from this object's value
   */
  public void subtract(Object decValue) {
    Object number1 = parseNumber(value);
    Object number2 = parseNumber(decValue);
    OperationType operationType = getOperationType(number1, number2);
    if(operationType == OperationType.INTEGER) {
      value = (Integer) number1 - (Integer) number2;
    }
    else {
      value = ((Number) number1).doubleValue() - ((Number) number2).doubleValue();
    }
  }

  /**
   * Multipies value stored in this object by given value. If both objects are
   * {@link Integer}s, performs {@link Integer} operation. If both objects are
   * {@link Double}, performs {@link Double} operation.
   * 
   * @param mulValue value with which this object's value will be multiplied
   */
  public void multiply(Object mulValue) {
    Object number1 = parseNumber(value);
    Object number2 = parseNumber(mulValue);
    OperationType operationType = getOperationType(number1, number2);
    if(operationType == OperationType.INTEGER) {
      value = (Integer) number1 * (Integer) number2;
    }
    else {
      value = ((Number) number1).doubleValue() * ((Number) number2).doubleValue();
    }
  }

  /**
   * Divides value stored in this object with given value. If both objects are
   * {@link Integer}s, performs {@link Integer} operation. If both objects are
   * {@link Double}, performs {@link Double} operation. Throws exception if
   * divided by 0;
   * 
   * @param divValue value by which this object's value will be divided
   * @throws IllegalArgumentException if divValue is 0
   */
  public void divide(Object divValue) {
    Object number1 = parseNumber(value);
    Object number2 = parseNumber(divValue);
    if(((Number) divValue).doubleValue() == 0) {
      throw new IllegalArgumentException("Divider can not be 0.");
    }
    OperationType operationType = getOperationType(number1, number2);
    if(operationType == OperationType.INTEGER) {
      value = (Integer) number1 / (Integer) number2;
    }
    else {
      value = ((Number) number1).doubleValue() / ((Number) number2).doubleValue();
    }
  }

  /**
   * Compares value stored in this object with given value. Returns -1 given value
   * is greater than this object's value, 0 if they are equal and 1 if this
   * object's value is greater than given value.
   * 
   * @param withValue value to be compared with this object's value
   * @return -1 if withValue is greater than this object's value, 0 if they are
   *         equal and 1 if this object's value is greater than given value
   */
  public int numCompare(Object withValue) {
    Object number1 = parseNumber(value);
    Object number2 = parseNumber(withValue);
    OperationType operationType = getOperationType(number1, number2);
    if(operationType == OperationType.INTEGER) {
      return ((Integer) number1).compareTo((Integer) number2);
    }
    return ((Double) number1).compareTo((Double) number2);
  }

  /**
   * Helper method used to parse number stored in given object. If
   * <code>null</code> object is given, returns 0.
   * 
   * @param number number to be parsed
   * @return parsed number
   */
  private Object parseNumber(Object number) {
    checkArgument(number);
    if(number == null) {
      return 0;
    }
    if(number instanceof String) {
      try {
        if(((String) number).indexOf('E') > 0 || ((String) number).indexOf('e') >= 0
            || ((String) number).indexOf('.') >= 0) {
          return Double.parseDouble((String) number);
        }
        else {
          return Integer.parseInt((String) number);
        }
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException(number + " can not be parsed to number.");
      }
    }
    return number;
  }

  /**
   * Helper method used to determine which operation type should be used in
   * calculating result based on given arguments. Returns {@link OperationType}
   * DOUBLE if at least 1 argument is of type {@link Double},
   * {@link OperationType} INTEGER if none of given arguments are of type
   * {@link Integer}.
   * 
   * @param value1 first value
   * @param value2 second value
   * @return {@link OperationType} DOUBLE if at least 1 argument is of type
   *         {@link Double}, {@link OperationType} INTEGER if none of given
   *         arguments are of type {@link Integer}
   */
  private OperationType getOperationType(Object value1, Object value2) {
    if(value1 instanceof Double || value2 instanceof Double) {
      return OperationType.DOUBLE;
    }
    return OperationType.INTEGER;
  }

  /**
   * Checks if given argument is valid for performing number operations on it.
   * Throws exception if it is not.
   * 
   * @param value argument to be checkedd
   * @throws IllegalArgumentException if value is valid argument
   */
  private void checkArgument(Object value) {
    if(!(value == null || value instanceof String || value instanceof Integer || value instanceof Double)) {
      throw new IllegalArgumentException("Value can only be null, String, Integer or Double.");
    }
  }

}
