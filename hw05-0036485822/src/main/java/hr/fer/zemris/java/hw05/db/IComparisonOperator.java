package hr.fer.zemris.java.hw05.db;

/**
 * Interface defines strategy pattern for comparing 2 {@link String} values with
 * comparison operator. Each class implementing this interface must define
 * satisfied method for comparing values with comparison operator.
 * 
 * @author tin
 *
 */
public interface IComparisonOperator {
  /**
   * Method determines if 2 values compared satisfy rule or rules defined by
   * comparison operator.
   * 
   * @param value1 first value
   * @param value2 second value
   * @return <code>true</code> if values satisfy operator rule or rules,
   *         <code>false</code> if they do not
   */
  public boolean satisfied(String value1, String value2);
}
