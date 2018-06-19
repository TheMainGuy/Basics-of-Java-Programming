package hr.fer.zemris.java.hw15.dao;

/**
 * Implements exception for {@link DAO} errors.
 * 
 * @author tin
 *
 */
public class DAOException extends RuntimeException {

  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Constructor.
   */
  public DAOException() {
  }

  /**
   * Constructor.
   * 
   * @param message message
   * @param cause cause
   * @param enableSuppression defines if suppression is enabled
   * @param writableStackTrace defines if there is writable stack trace
   */
  public DAOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  /**
   * Constructor.
   * 
   * @param message message
   * @param cause cause
   */
  public DAOException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructor.
   * 
   * @param message message
   */
  public DAOException(String message) {
    super(message);
  }

  /**
   * Constructor.
   * 
   * @param cause cause
   */
  public DAOException(Throwable cause) {
    super(cause);
  }
}