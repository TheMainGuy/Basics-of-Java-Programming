package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * This class implements query lexer. After it is initialized with string,
 * separates tokens in query on demand.
 * 
 * @author tin
 *
 */
public class QueryLexer {
  private char[] data;
  private Token token;
  private int currentIndex;

  public QueryLexer(String query) {
    if(query == null) {
      throw new NullPointerException("Query can not be null.");
    }
    data = query.toCharArray();
    token = null;
    currentIndex = 0;
  }

  /**
   * Method which iterates to next token in text and returns it.
   * 
   * @return next token
   * @throws LexerException if there are no tokens left
   */
  public Token nextToken() {
    skipWhiteSpaces();
    if(token != null && token.getType() == TokenType.EOF) {
      throw new LexerException("No tokens left.");
    }

    if(currentIndex >= data.length) {
      token = new Token(TokenType.EOF, null);
    }
    else {
      tokenize();
    }

    return getToken();
  }

  /**
   * Helper method which sets next {@link Token} value. Called by method
   * nextToken().
   * 
   * @throws LexerException if tag is opened inside another tag
   */
  private void tokenize() {
    if(data[currentIndex] == 'j') {
      tokenizeWord("jmbag");
    }
    else if(data[currentIndex] == 'f') {
      tokenizeWord("firstName");
    }
    else if(data[currentIndex] == 'l') {
      tokenizeWord("lastName");
    }
    else if(data[currentIndex] == 'a' || data[currentIndex] == 'A') {
      tokenizeKeyword(new String(data).substring(currentIndex, currentIndex + 3));
    }
    else if(data[currentIndex] == 'L') {
      tokenizeOperator();
    }
    else if(data[currentIndex] == '<' || data[currentIndex] == '=' || data[currentIndex] == '>'
        || data[currentIndex] == '!') {
      tokenizeOperator();
    }
    else if(data[currentIndex] == '"') {
      tokenizeStringLiteral();
    }
  }

  /**
   * Used for tokenizing string literal. String literal is surounded by 2 "
   * symbols. No escaping is allowed inside string literal. When this method
   * finishes, token will be set to tokenized string literal with "" included.
   * 
   * @throws LexerException if string literal is not closed with "
   */
  private void tokenizeStringLiteral() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("\"");
    currentIndex++;
    while (true) {
      if(currentIndex >= data.length) {
        throw new LexerException("Close \" expected.");
      }
      if(data[currentIndex] == '"') {
        stringBuilder.append("\"");
        token = new Token(TokenType.STRING_LITERAL, stringBuilder.toString());
        currentIndex++;
        return;
      }
      stringBuilder.append(data[currentIndex]);
      currentIndex++;
    }

  }

  /**
   * Used for tokenizing keywords. In this lexer, only keyword is case insensitive
   * word AND. When this method finishes, token will be set to word AND.
   * 
   * @throws LexerException if AND word starts but is not finished.
   */
  private void tokenizeKeyword(String string) {
    if(data.length <= currentIndex + 2) {
      throw new LexerException("AND operator expected but query is too short.");
    }
    if(string.toLowerCase().equals("and")) {
      token = new Token(TokenType.KEYWORD, "AND");
      currentIndex += 3;
    }
    else {
      throw new LexerException("AND operator started but not finished.");
    }

  }

  /**
   * Used for tokenizing operators. Valid operators are: LIKE, <, <=, >, >=, = and
   * !=. When this method finishes, token will be set to tokenized operator.
   * 
   * @throws LexerException if LIKE operator starts but is not finished
   * @throws LexerException if != operator starts but is not finished
   */
  private void tokenizeOperator() {
    if(data[currentIndex] == 'L') {
      if(data.length <= currentIndex + 3) {
        throw new LexerException("LIKE operator expected but query is too short.");
      }
      if(data[currentIndex + 1] == 'I' && data[currentIndex + 2] == 'K' && data[currentIndex + 3] == 'E') {
        token = new Token(TokenType.OPERATOR, "LIKE");
        currentIndex += 4;
      }
      else {
        throw new LexerException("LIKE operator expected");
      }
    }

    else if(data[currentIndex] == '<') {
      if(data.length <= currentIndex + 1) {
        token = new Token(TokenType.OPERATOR, "<");
        currentIndex++;
      }
      else {
        if(data[currentIndex + 1] == '=') {
          token = new Token(TokenType.OPERATOR, "<=");
          currentIndex += 2;
        }
        else {
          token = new Token(TokenType.OPERATOR, "<");
          currentIndex++;
        }
      }
    }

    else if(data[currentIndex] == '>') {
      if(data.length <= currentIndex + 1) {
        token = new Token(TokenType.OPERATOR, ">");
        currentIndex++;
      }
      else {
        if(data[currentIndex + 1] == '=') {
          token = new Token(TokenType.OPERATOR, ">=");
          currentIndex += 2;
        }
        else {
          token = new Token(TokenType.OPERATOR, ">");
          currentIndex++;
        }
      }
    }

    else if(data[currentIndex] == '=') {
      token = new Token(TokenType.OPERATOR, "=");
      currentIndex++;
    }

    else if(data[currentIndex] == '!') {
      if(data.length <= currentIndex + 1) {
        throw new LexerException("!= operator expected");
      }
      else {
        if(data[currentIndex + 1] == '=') {
          token = new Token(TokenType.OPERATOR, "!=");
          currentIndex+=2;
        }
      }
    }

  }

  /**
   * Used for tokenizing words. In this lexer, only words allowed are: firstName,
   * lastName and jmbag. When this method finishes, token will be set to tokenized
   * word.
   * 
   * @throws LexerException if any word starts but is not finished.
   */
  private void tokenizeWord(String string) {
    if(data[currentIndex] == 'f') {
      if(data.length <= currentIndex + 8) {
        throw new LexerException("firstName expected but query is too short.");
      }
      if(new String(data).substring(currentIndex, currentIndex + 9).equals("firstName")) {
        token = new Token(TokenType.FIELD, "firstName");
        currentIndex += 9;
      }
      else {
        throw new LexerException("firstName expected");
      }
    }

    else if(data[currentIndex] == 'l') {
      if(data.length <= currentIndex + 7) {
        throw new LexerException("lastName expected but query is too short.");
      }
      if(new String(data).substring(currentIndex, currentIndex + 8).equals("lastName")) {
        token = new Token(TokenType.FIELD, "lastName");
        currentIndex += 8;
      }
      else {
        throw new LexerException("lastName expected");
      }
    }

    else if(data[currentIndex] == 'j') {
      if(data.length <= currentIndex + 4) {
        throw new LexerException("jmbag expected but query is too short.");
      }
      if(new String(data).substring(currentIndex, currentIndex + 5).equals("jmbag")) {
        token = new Token(TokenType.FIELD, "jmbag");
        currentIndex += 5;
      }
      else {
        throw new LexerException("jmbag expected");
      }
    }

  }

  /**
   * Skips white spaces directly on and after currentIndex in text.
   */
  private void skipWhiteSpaces() {
    while (currentIndex < data.length) {
      if(Character.isWhitespace(data[currentIndex])) {
        currentIndex++;
      }
      else {
        return;
      }
    }
  }

  /**
   * Returns current token.
   * 
   * @return current token
   */
  public Token getToken() {
    return token;
  }

}
