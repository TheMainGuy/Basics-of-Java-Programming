package hr.fer.zemris.java.hw05.db;

/**
 * Class implements operator constants. Each operator constant implements 1
 * lambda {@link Boolean} method for comparing 2 {@link String} values and
 * returns <code>true</code> if values satisfy compare operator and
 * <code>false</code> if they do not.
 * 
 * @author tin
 *
 */
public class ComparisonOperators {

  // Represents less operator. Method satisfied returns true if value1 is
  // lexicographically before value2 and false if it is not
  public static final IComparisonOperator LESS = (value1, value2) -> {
    if(value1.compareTo(value2) < 0) {
      return true;
    }
    return false;
  };

  // Represents less than or equal operator. Method satisfied returns true if
  // value1 is lexicographically before value2 or they are equal and false if it
  // is not
  public static final IComparisonOperator LESS_OR_EQUALS = (value1, value2) -> {
    if(value1.compareTo(value2) <= 0) {
      return true;
    }
    return false;
  };

  // Represents greater operator. Method satisfied returns true if value1 is
  // lexicographically after value2 and false if it is not
  public static final IComparisonOperator GREATER = (value1, value2) -> {
    if(value1.compareTo(value2) > 0) {
      return true;
    }
    return false;
  };

  // Represents greater than or equal operator. Method satisfied returns true if
  // value1 is lexicographically after value2 or they are equal and false if it
  // is not
  public static final IComparisonOperator GREATER_OR_EQUALS = (value1, value2) -> {
    if(value1.compareTo(value2) >= 0) {
      return true;
    }
    return false;
  };

  // Represents equals operator. Method satisfied returns true if value1 and
  // value2
  // are equal
  public static final IComparisonOperator EQUALS = (value1, value2) -> {
    if(value1.compareTo(value2) == 0) {
      return true;
    }
    return false;
  };

  // Represents not equals operator. Method satisfied returns true if value1 and
  // value2
  // are not equal
  public static final IComparisonOperator NOT_EQUALS = (value1, value2) -> {
    if(value1.compareTo(value2) != 0) {
      return true;
    }
    return false;
  };

  // Represents like operator. value2 can have 1 '*' character that can replace
  // any number of any characters. Method satisfied returns true if value1 can be
  // expressed with value2 expression and false if it can not.
  public static final IComparisonOperator LIKE = (value1, value2) -> {
    int counter = 0;
    for(int i = 0; i < value2.length();i++) {
      if(value2.charAt(i) == '*') {
        counter++;
      }
    }
    if(counter > 1) {
      throw new IllegalStateException("Maximum of 1 \'*\' character allowed.");
    }
    value2 = value2.replace("*", ".*");
    
    if(value1.matches(value2)) {
      return true;
    }
    return false;
  };

}
