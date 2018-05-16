package hr.fer.zemris.java.hw03.prob1;

/**
 * Class which represents lexer. After it is initialized with string, separate
 * tokens in text on demand.
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
    state = LexerState.BASIC;
  }

  public Token nextToken() {
    if(token != null && token.getType() == TokenType.EOF) {
      throw new LexerException("No tokens left.");
    }

    skipWhiteSpaces();

    if(currentIndex >= data.length) {
      token = new Token(TokenType.EOF, null);
    }

    else {
      tokenize();
    }

    return getToken();
  }

  private void tokenize() {
    if(Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
      tokenizeWord();
    }
    else if(Character.isDigit(data[currentIndex])) {
      if(state == LexerState.BASIC) {
        tokenizeNumber();
      }
      else {
        tokenizeWord();
      }

    }
    else {
      if(state == LexerState.BASIC || data[currentIndex] == '#') {
        token = new Token(TokenType.SYMBOL, data[currentIndex]);
        currentIndex++;
      }
      else {
        tokenizeWord();
      }
      
    }
  }

  private void tokenizeNumber() {
    long number = 0;
    while (currentIndex < data.length) {
      if(Character.isDigit(data[currentIndex])) {
        number *= 10;
        number += Character.getNumericValue(data[currentIndex]);
        if(number < 0) {
          throw new LexerException("Numbers to be tokenized should be smaller than 2 on the power of 63 - 1.");
        }
      }
      else {
        break;
      }
      currentIndex++;
    }
    token = new Token(TokenType.NUMBER, number);
    return;
  }

  private void tokenizeWord() {
    StringBuilder stringBuilder = new StringBuilder();
    while (currentIndex < data.length) {
      if(data[currentIndex] == '\\') {
        if(state == LexerState.BASIC) {
          if(currentIndex + 1 >= data.length) {
            throw new LexerException("\\ can not be last character.");
          }

          if(Character.isDigit(data[currentIndex + 1])) {
            stringBuilder.append(data[currentIndex + 1]);
            currentIndex++;
          }
          else if(data[currentIndex + 1] == '\\') {
            stringBuilder.append('\\');
            currentIndex++;
          }
          else {
            throw new LexerException("Only a digit or \\ can follow after \\");
          }
        }
        else {
          stringBuilder.append(data[currentIndex]);
        }
      }
      else if(Character.isLetter(data[currentIndex]) || (Character.isDigit(data[currentIndex]) && state == LexerState.EXTENDED)) {
        stringBuilder.append(data[currentIndex]);
      }
      else {
        break;
      }
      currentIndex++;
    }
    token = new Token(TokenType.WORD, stringBuilder.toString());
  }

  public Token getToken() {
    return token;
  }

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

  public void setState(LexerState newState) {
    if(newState == null) {
      throw new NullPointerException("State can not be null");
    }
    state = newState;
  }

}
