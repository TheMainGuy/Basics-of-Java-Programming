package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Exception which should be thrown if something goes wrong in parser.
 * 
 * @author tin
 *
 */
public class SmartScriptParserException extends RuntimeException{
  private static final long serialVersionUID = 1L;

  /**
   * Constructor.
   */
  public SmartScriptParserException() {
    super();
  }
  
  /**
   * Constructor with message
   * 
   * @param message message
   */
  public SmartScriptParserException(String message) {
    super(message);
  }
}
