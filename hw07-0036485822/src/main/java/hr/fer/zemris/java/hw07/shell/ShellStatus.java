package hr.fer.zemris.java.hw07.shell;

/**
 * Represents basic shell status used for communicating between shell commands
 * and shell. Shell status CONTINUE signals shell that it should continue with
 * work. Shell status TERMINATE signals shell it should be terminated.
 * 
 * @author tin
 *
 */
public enum ShellStatus {
  /**
   * Represents shell status in which shell continues with prompting user for
   * input and calling shell commands.
   */
  CONTINUE,

  /**
   * Represents shell status in which shell stops prompting user for input and
   * starts to terminate itself.
   */
  TERMINATE
}
