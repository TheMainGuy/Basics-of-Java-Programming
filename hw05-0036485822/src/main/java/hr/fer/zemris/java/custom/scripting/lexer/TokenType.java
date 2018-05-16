package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Represents {@link QueryLexer} token types.
 * 
 * @author tin
 *
 */
public enum TokenType {
  // Represents student record field.
  FIELD,
  
  // Represetns string literal used for comparing student record field with it.
  STRING_LITERAL,
  
  // Represents comparison operator.
  OPERATOR,
  
  // Represents query keyword.
  KEYWORD,
  
  // Represents end of query.
  EOF
}
