package hr.fer.zemris.java.hw05.db;

/**
 * Class represents one conditional expression containing fieldGetter for
 * getting one {@link StudentRecord} field, stringLiteral containing
 * {@link String} with which the field will be compared and comparison operator
 * used for comparing them.
 * 
 * @author tin
 *
 */
public class ConditionalExpression {
  // Field getter used to get field from student record
  IFieldValueGetter fieldGetter;

  // String literal used to compare field to
  String stringLiteral;

  // Operator which will be used to compare string literal to field
  IComparisonOperator comparisonOperator;

  /**
   * Constructor. Creates instance with 3 parameters.
   * 
   * @param fieldGetter {@link StudentRecord} field
   * @param stringLiteral string literal
   * @param comparisonOperator comparison operator
   */
  public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral,
      IComparisonOperator comparisonOperator) {
    this.fieldGetter = fieldGetter;
    this.stringLiteral = stringLiteral;
    this.comparisonOperator = comparisonOperator;
  }

  /**
   * Returns field getter.
   * 
   * @return field getter
   */
  public IFieldValueGetter getFieldGetter() {
    return fieldGetter;
  }

  /**
   * Returns string literal.
   * 
   * @return string literal
   */
  public String getStringLiteral() {
    return stringLiteral;
  }

  /**
   * Returns comparison operator.
   * @return comparison operator
   */
  public IComparisonOperator getComparisonOperator() {
    return comparisonOperator;
  }

}
