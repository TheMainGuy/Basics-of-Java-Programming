package hr.fer.zemris.java.hw07.shell;

import java.util.List;

/**
 * Defines methods each shell command must implement. Shell commands can
 * communicate with other classes via executeCommand return value and altering
 * {@link Environment} env.
 * 
 * @author tin
 *
 */
public interface ShellCommand {
  /**
   * Executes command. Any result from executing this command will be a return
   * value or change in given {@link Environment} env. {@link ShellStatus}
   * CONTINUE is returned if shell should continue with work, {@link ShellStatus}
   * TERMINATE is returned if shell should be terminated.
   * 
   * @param env {@link Environment} on which this command is executed
   * @param arguments arguments given after command name
   * @return {@link ShellStatus} CONTINUE if shell should continue with work,
   *         {@link ShellStatus} TERMINATE if shell should be terminated after
   *         executing this command
   */
  ShellStatus executeCommand(Environment env, String arguments);

  /**
   * Returns command name.
   * 
   * @return command name
   */
  String getCommandName();

  /**
   * Returns command description.
   * 
   * @return command description
   */
  List<String> getCommandDescription();
}
