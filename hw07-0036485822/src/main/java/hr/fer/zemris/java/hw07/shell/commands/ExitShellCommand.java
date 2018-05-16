package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.MyShell;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Implements exit command which takes no arguments and terminates shell.
 * 
 * @author tin
 *
 */
public class ExitShellCommand implements ShellCommand {

  /**
   * Defines exit string which user can use to exit {@link MyShell}.
   */
  public static final String EXIT_COMMAND = "exit";

  @Override
  public ShellStatus executeCommand(Environment env, String arguments) {
    if(ArgumentParser.getArguments(arguments).size() == 0) {
      return ShellStatus.TERMINATE;
    }
    env.writeln("Command " + EXIT_COMMAND + " can not have any arguments.");
    return ShellStatus.CONTINUE;
  }

  @Override
  public String getCommandName() {
    return EXIT_COMMAND;
  }

  @Override
  public List<String> getCommandDescription() {
    List<String> description = new ArrayList<>();
    description.add("Command " + EXIT_COMMAND + " terminates current shell.");
    description.add("If one or more arguments are provided, shell will "
        + "not be terminated and appropriate message will be written.");
    return description;
  }

}
