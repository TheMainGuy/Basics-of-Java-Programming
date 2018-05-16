package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Class which represents {@link QueryLexer} token. It has value and type.
 * 
 * @author tin
 *
 */
public class Token {
  private Object value;
  private TokenType type;

  /**
   * Creates new lexer token.
   * 
   * @param type token type
   * @param value token value
   * @throws IllegalArgumentException if type is null
   * @throws IllegalArgumentException if value is null and type is not EOF
   */
  public Token(TokenType type, Object value) {
    if(type == null) {
      throw new IllegalArgumentException("Token type can not be null");
    }
    if(value == null && type != TokenType.EOF) {
      throw new IllegalArgumentException("Token value can not be null if type is not EOF");
    }
    this.value = value;
    this.type = type;
  }

  /**
   * Returns token value
   * 
   * @return value
   */
  public Object getValue() {
    return value;
  }

  /**
   * Returns token type
   * 
   * @return type
   */
  public TokenType getType() {
    return type;
  }

  /**
   * Returns token value converted to string.
   * 
   * @return if value is null empty string, token value converted to string otherwise
   */
  @Override
  public String toString() {
    if(value == null) {
      return "";
    }
    return value.toString();
  }

}
