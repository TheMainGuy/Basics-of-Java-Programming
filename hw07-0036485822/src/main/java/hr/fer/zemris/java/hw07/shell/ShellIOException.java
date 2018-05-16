package hr.fer.zemris.java.hw07.shell;

public class ShellIOException extends RuntimeException {

  /**
   * Default serial version UID
   */
  private static final long serialVersionUID = 1L;
  
  /**
   * Constructor.
   */
  public ShellIOException() {
    super();
  }
  
  /**
   * Contructor with message.
   * 
   * @param message message
   */
  public ShellIOException(String message) {
    super(message);
  }
}
