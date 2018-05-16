package hr.fer.zemris.java.hw05.db;

import java.util.List;

/**
 * Class used to filter {@link StudentRecord}s based on query expressions.
 * 
 * @author tin
 *
 */
public class QueryFilter implements IFilter {
  // List of conditional expressions.
  private List<ConditionalExpression> conditionalExpressions;

  /**
   * Constructor.
   * 
   * @param conditionalExpressions {@link List} of conditional expressions
   */
  public QueryFilter(List<ConditionalExpression> conditionalExpressions) {
    this.conditionalExpressions = conditionalExpressions;
  }

  /**
   * Returns true if student record {@link StudentRecord} satisfies all
   * conditional expressions from conditionalExpressions {@link List}.
   * 
   * @param record student record
   * @return <code>true</code> if student record satisfies all conditional
   *         expressions, <code>false</code> if it does not
   */
  @Override
  public boolean accepts(StudentRecord record) {
    for (ConditionalExpression expression : conditionalExpressions) {
      if(!expression.getComparisonOperator().satisfied(expression.getFieldGetter().get(record),
          expression.getStringLiteral())) {
        return false;
      }
    }
    return true;
  }

}
