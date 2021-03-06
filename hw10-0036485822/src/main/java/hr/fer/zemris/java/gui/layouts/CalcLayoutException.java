package hr.fer.zemris.java.gui.layouts;

/**
 * Exception thrown when unexpected request from {@link CalcLayout} appears.
 * 
 * @author tin
 *
 */
public class CalcLayoutException extends RuntimeException {

  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Constructor.
   * 
   * @param message exception message
   */
  public CalcLayoutException(String message) {
    super(message);
  }

  @Override
  public String getMessage() {
    return super.getMessage();
  }

}
