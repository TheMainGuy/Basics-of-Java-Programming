package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.custom.scripting.lexer.QueryLexer;
import hr.fer.zemris.java.custom.scripting.lexer.Token;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;

/**
 * Class implments query statement parser. It parses query {@link String} to get
 * query fields, operators and keywords. Query statements must be separated with
 * case insensitive AND keyword. Query with one statement in jmbag = "something"
 * is direct query.
 * 
 * @author tin
 *
 */
public class QueryParser {
  // Query lexer used for tokenizing query strings
  private QueryLexer lexer;
  private List<ConditionalExpression> conditionalExpressions;

  /**
   * Creates parser from query {@link String}
   * 
   * @param query query {@link String}
   * @throws NullPointerException if query is null
   */
  public QueryParser(String query) {
    if(query == null) {
      throw new NullPointerException("Query can not be null.");
    }
    lexer = new QueryLexer(query);
    conditionalExpressions = new ArrayList<>();
    fillConditionalExpressions();
  }

  /**
   * Parses conditional expressions from query and fills conditionalExpressions
   * {@link List} with them.
   * 
   * @throws IllegalArgumentException if query is not in valid form
   */
  private void fillConditionalExpressions() {
    while (true) {
      Token tokenField = lexer.nextToken();
      if(checkForEOF(tokenField)) {
        throw new IllegalArgumentException("Query is not in valid form.");
      }
      Token tokenOperator = lexer.nextToken();
      if(checkForEOF(tokenOperator)) {
        throw new IllegalArgumentException("Query is not in valid form.");
      }
      Token tokenLiteral = lexer.nextToken();
      if(checkForEOF(tokenLiteral)) {
        throw new IllegalArgumentException("Query is not in valid form.");
      }

      if(tokenField.getType() == TokenType.FIELD && tokenOperator.getType() == TokenType.OPERATOR
          && tokenLiteral.getType() == TokenType.STRING_LITERAL) {
        addTokensToConditionalExpressions(tokenField, tokenOperator, tokenLiteral);
      }
      else {
        throw new IllegalArgumentException("Query is not in valid form.");
      }

      Token tokenAnd = lexer.nextToken();
      if(checkForEOF(tokenAnd)) {
        return;
      }
      if(!tokenAnd.getValue().toString().toLowerCase().equals("and")) {
        throw new IllegalArgumentException("Query is not in valid form.");
      }
    }
  }

  /**
   * Checks for EOF token. Returns <code>true</code> if token if EOF token
   * and <code>false</code> if it is not.
   * 
   * @param token token to be checked
   * @return <code>true</code> if token is EOF token, <code>false</code>
   *         if it is not.
   */
  private boolean checkForEOF(Token token) {
    if(token.getType() == TokenType.EOF) {
      return true;
    }
    return false;
  }

  /**
   * Helper method which creates new {@link ConditionalExpression} from 3 tokens
   * and saves it to conditionalExpressions {@link List}. Throws exception if they
   * are not in valid format.
   * 
   * @param tokenField fieldGetter saved in token
   * @param tokenOperator operator saved in token
   * @param tokenLiteral string literal saved in token
   * @throws IllegalArgumentException if tokenField contains unknown string
   * @throws IllegalArgumentException if tokenOperator contains unknown operator
   */
  private void addTokensToConditionalExpressions(Token tokenField, Token tokenOperator, Token tokenLiteral) {
    IFieldValueGetter fieldGetter;
    if(tokenField.getValue().toString().equals("firstName")) {
      fieldGetter = FieldValueGetters.FIRST_NAME;
    }
    else if(tokenField.getValue().toString().equals("lastName")) {
      fieldGetter = FieldValueGetters.LAST_NAME;
    }
    else if(tokenField.getValue().toString().equals("jmbag")) {
      fieldGetter = FieldValueGetters.JMBAG;
    }
    else {
      throw new IllegalArgumentException("Query is not in valid form.");
    }

    IComparisonOperator comparisonOperator;
    if(tokenOperator.getValue().toString().equals("<")) {
      comparisonOperator = ComparisonOperators.LESS;
    }
    else if(tokenOperator.getValue().toString().equals("<=")) {
      comparisonOperator = ComparisonOperators.LESS_OR_EQUALS;
    }
    else if(tokenOperator.getValue().toString().equals(">")) {
      comparisonOperator = ComparisonOperators.GREATER;
    }
    else if(tokenOperator.getValue().toString().equals(">=")) {
      comparisonOperator = ComparisonOperators.GREATER_OR_EQUALS;
    }
    else if(tokenOperator.getValue().toString().equals("=")) {
      comparisonOperator = ComparisonOperators.EQUALS;
    }
    else if(tokenOperator.getValue().toString().equals("!=")) {
      comparisonOperator = ComparisonOperators.NOT_EQUALS;
    }
    else if(tokenOperator.getValue().toString().equals("LIKE")) {
      comparisonOperator = ComparisonOperators.LIKE;
    }
    else {
      throw new IllegalArgumentException("Query is not in valid form.");
    }

    String stringLiteral = tokenLiteral.getValue().toString().substring(1,
        tokenLiteral.getValue().toString().length() - 1);
    ConditionalExpression expression = new ConditionalExpression(fieldGetter, stringLiteral, comparisonOperator);
    conditionalExpressions.add(expression);
  }

  /**
   * Checks if query is direct and returns <code>true</code> if it is,
   * <code>false</code> if it is not. Direct query is defined by having only one
   * {@link ConditionalExpression} which is in jmbag = "something" form.
   * 
   * @return <code>true</code> if query is direct, <code>false</code> if it is not
   */
  public boolean isDirectQuery() {
    if(conditionalExpressions.size() != 1) {
      return false;
    }
    if(conditionalExpressions.get(0).getFieldGetter() == FieldValueGetters.JMBAG
        && conditionalExpressions.get(0).getComparisonOperator() == ComparisonOperators.EQUALS) {
      return true;
    }
    return false;
  }

  /**
   * If query is direct, returns string literal for jmbag comparison. if it is
   * not, throws exception.
   * 
   * @return jmbag string literal
   * @throws IllegalStateException if query is not direct query
   */
  public String getQueriedJMBAG() {
    if(isDirectQuery()) {
      return conditionalExpressions.get(0).getStringLiteral();
    }
    throw new IllegalStateException("Query is not direct query.");
  }

  /**
   * Returns {@link List} of all {@link ConditionalExpression}s parsed from query
   * string.
   * 
   * @return conditionalExpression list
   */
  public List<ConditionalExpression> getQuery() {
    return conditionalExpressions;
  }

}
