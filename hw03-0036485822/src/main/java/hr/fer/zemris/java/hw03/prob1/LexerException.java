package hr.fer.zemris.java.hw03.prob1;

/**
 * Exception which should be thrown if something goes wrong in lexer.
 * 
 * @author tin
 *
 */
public class LexerException extends RuntimeException {

  private static final long serialVersionUID = 5019575938076748881L;

  /**
   * Constructor.
   */
  public LexerException() {
    super();
  }

  /**
   * Contructor with message.
   * 
   * @param message message
   */
  public LexerException(String message) {
    super(message);
  }
}
