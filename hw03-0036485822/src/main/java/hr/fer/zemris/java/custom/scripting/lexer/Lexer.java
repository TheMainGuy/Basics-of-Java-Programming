package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * This class represents lexer. After it is initialized with string, separate
 * tokens in text on demand.
 * 
 * @author tin
 *
 */
public class Lexer {
  private char[] data;
  private Token token;
  private int currentIndex;
  LexerState state;

  /**
   * Creates instance of lexer.
   * 
   * @param text text used to initialize lexer which it will use to tokenize
   * @throws NullPointerException if text is null
   */
  public Lexer(String text) {
    if(text == null) {
      throw new NullPointerException("Lexer constructor argument can not be null.");
    }
    data = text.toCharArray();
    token = null;
    currentIndex = 0;
    state = LexerState.TEXT;
  }

  /**
   * Method which iterates to next token in text and returns it.
   * 
   * @return next token
   * @throws LexerException if there are no tokens left
   */
  public Token nextToken() {
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
   * Help method which sets next token value. Called by method nextToken().
   * 
   * @throws LexerException if tag is opened inside another tag
   */
  private void tokenize() {
    if(state == LexerState.TEXT && !checkForOpenTag()) {
      tokenizeText();
    }
    else if(state == LexerState.TEXT && checkForOpenTag()) {
      token = new Token(TokenType.TAG_OPEN, "{$");
      currentIndex += 2;
    }
    else if(state == LexerState.TAG && checkForOpenTag()) {
      throw new LexerException("Tag can not be open inside another tag.");
    }
    else {
      tokenizeTag();
    }
  }

  /**
   * Method used to tokenize normal text. Tokenizes everything until EOF or open
   * tag.
   *
   * @throws LexerException if illegal escaping occurs.
   */
  private void tokenizeText() {
    StringBuilder stringBuilder = new StringBuilder();
    while (currentIndex < data.length) {
      if(checkForOpenTag()) {
        break;
      }
      if(data[currentIndex] == '\\') {
        if(data[currentIndex + 1] == '\\') {
          stringBuilder.append("\\");
          currentIndex += 2;
        }
        else if(data[currentIndex + 1] == '{') {
          stringBuilder.append("{");
          currentIndex += 2;
        }
        else {
          throw new LexerException("\\ must precede one of the following characters: \\, {.");
        }
      }
      else {
        stringBuilder.append(data[currentIndex]);
        currentIndex++;
      }
    }
    token = new Token(TokenType.TEXT, stringBuilder.toString());

  }

  /**
   * Method which tokenizes tag elements and closed tag.
   * 
   * @throws LexerException if there are unsupported characters in tags.
   */
  private void tokenizeTag() {
    skipWhiteSpaces();
    if(data[currentIndex] == '=' || Character.isLetter(data[currentIndex])) {
      tokenizeName();
    }
    else if(data[currentIndex] == '\"') {
      tokenizeString();
    }
    else if(data[currentIndex] == '@') {
      tokenizeFunction();
    }
    else if(Character.isDigit(data[currentIndex])) {
      tokenizeNumber();
    }
    else if(data[currentIndex] == '-' && Character.isDigit(data[currentIndex + 1])) {
      tokenizeNumber();
    }
    else if(checkForCloseTag()) {
      token = new Token(TokenType.TAG_CLOSE, "$}");
      currentIndex += 2;
    }
    else {
      ArrayIndexedCollection operators = new ArrayIndexedCollection(5);
      operators.add('+');
      operators.add('-');
      operators.add('*');
      operators.add('/');
      operators.add('^');
      if(operators.contains(data[currentIndex])) {
        token = new Token(TokenType.OPERATOR, data[currentIndex]);
        currentIndex++;
      }
      else {
        throw new LexerException("Unsupported character.");
      }
    }
  }

  /**
   * Method which checks if open tag is next token.
   * 
   * @return true if open tag is next token, false otherwise.
   */
  private boolean checkForOpenTag() {
    if(data[currentIndex] == '{' && currentIndex + 1 < data.length && data[currentIndex + 1] == '$') {
      if(currentIndex > 0 && data[currentIndex - 1] == '\\') {
        return false;
      }
      else {
        return true;
      }
    }

    return false;
  }

  /**
   * Method which checks if close tag is next token.
   * 
   * @return true if close tag is next token, false otherwise.
   */
  private boolean checkForCloseTag() {
    if(data[currentIndex] == '$' && currentIndex + 1 < data.length && data[currentIndex + 1] == '}') {
      return true;
    }
    return false;
  }

  /**
   * Method which tokenizes tag names.
   * 
   * @throws LexerException if tag name has invalid name.
   */
  private void tokenizeName() {
    if(data[currentIndex] == '=') {
      token = new Token(TokenType.NAME, "=");
      currentIndex++;
    }
    else {
      try {
        token = new Token(TokenType.NAME, variableName());
        currentIndex += variableName().length();

      } catch (LexerException e) {
        throw new LexerException("Tag name must start with a letter or be \"=\".");
      }

    }
  }

  /**
   * Method which tokenizes function elements.
   */
  private void tokenizeFunction() {
    StringBuilder stringBuilder = new StringBuilder();
    currentIndex++;
    stringBuilder.append(variableName());
    currentIndex += stringBuilder.length();
    token = new Token(TokenType.FUNCTION, stringBuilder.toString());
  }

  /**
   * Method which tokenizes string elements.
   * 
   * @throws LexerException if illegal escaping occurs
   */
  private void tokenizeString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("\"");
    currentIndex++;
    while (currentIndex < data.length) {
      if(data[currentIndex] == '\"') {
        stringBuilder.append("\"");
        token = new Token(TokenType.STRING, stringBuilder.toString());
        currentIndex++;
        return;
      }
      else if(data[currentIndex] == '\\') {
        if(data[currentIndex + 1] == '\\') {
          stringBuilder.append("\\");
          currentIndex += 2;
        }
        else if(data[currentIndex + 1] == '\"') {
          stringBuilder.append("\"");
          currentIndex += 2;
        }
        else if(data[currentIndex + 1] == 'n') {
          stringBuilder.append("n");
          currentIndex += 2;
        }
        else if(data[currentIndex + 1] == 'r') {
          stringBuilder.append("r");
          currentIndex += 2;
        }
        else if(data[currentIndex + 1] == 't') {
          stringBuilder.append("t");
          currentIndex += 2;
        }
        else {
          throw new LexerException("Symbol \\ must precede one of the following characters: \", \\, n, r, t.");
        }
      }
      stringBuilder.append(data[currentIndex]);
      currentIndex++;
    }
    throw new LexerException("\" must be closed with another \".");
  }

  /**
   * Method which returns variable name without increasing index. Can be used for
   * functions after @, or anything with same syntax.
   * 
   * @return variable name
   * @throws LexerException if variable name doesn't start with a letter
   */
  private String variableName() {
    StringBuilder stringBuilder = new StringBuilder();
    int indexTemp = currentIndex;
    if(!Character.isLetter(data[indexTemp])) {
      throw new LexerException("Variable name must start with a letter.");
    }
    while (indexTemp < data.length) {
      if(Character.isLetter(data[indexTemp]) || data[indexTemp] == '_' || Character.isDigit(data[indexTemp])) {
        stringBuilder.append(data[indexTemp]);
        indexTemp++;
      }
      else {
        break;
      }
    }
    return stringBuilder.toString();
  }

  /**
   * Method which tokenizes integer and double numbers.
   * 
   * @throws LexerException if number ends with .
   */
  private void tokenizeNumber() {
    int positivity = 1;
    if(data[currentIndex] == '-') {
      positivity = -1;
      currentIndex++;
    }
    double number = 0;
    double decimalFactor = 0.1;
    boolean isDecimal = false;
    while (currentIndex < data.length) {
      if(!isDecimal) {
        if(Character.isDigit(data[currentIndex])) {
          number *= 10;
          number += Character.getNumericValue(data[currentIndex]);
        }
        else if(data[currentIndex] == '.') {
          isDecimal = true;
        }
        else {
          break;
        }
        currentIndex++;
      }
      else {
        if(Character.isDigit(data[currentIndex])) {
          number += decimalFactor * Character.getNumericValue(data[currentIndex]);
          decimalFactor /= 10;
          currentIndex++;
        }
        else {
          break;
        }
      }

    }
    if(isDecimal && decimalFactor == 0.1) {
      throw new LexerException("Number can not end with \".\".");
    }
    if(isDecimal) {
      token = new Token(TokenType.DOUBLE_NUMBER, number * positivity);
    }
    else {
      Double doubleNumber = number;
      token = new Token(TokenType.INTEGER_NUMBER, doubleNumber.intValue() * positivity);
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

  /**
   * Method which skips white spaces directly on and after currentIndex in text.
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
   * Sets lexer state to newState
   * 
   * @param newState state
   * @throws NullPointerException if newState is null
   */
  public void setState(LexerState newState) {
    if(newState == null) {
      throw new NullPointerException("State can not be null");
    }
    state = newState;
  }

}
