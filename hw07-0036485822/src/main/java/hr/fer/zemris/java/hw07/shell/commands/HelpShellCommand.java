package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.EnvironmentImpl;
import hr.fer.zemris.java.hw07.shell.MyShell;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Implements help command for {@link MyShell}. If called without arguments,
 * prints out every available command. If called with command name as argument,
 * displays detailed command description.
 * 
 * @author tin
 *
 */
public class HelpShellCommand implements ShellCommand {

  /**
   * Defines help string which user can use to get information about commands or
   * list them.
   */
  public static final String HELP_COMMAND = "help";

  @Override
  public ShellStatus executeCommand(Environment env, String arguments) {
    List<String> args = ArgumentParser.getArguments(arguments);
    if(args.size() > 1) {
      env.write("Command " + HELP_COMMAND + " must have none or one argument.");
      return ShellStatus.CONTINUE;
    }
    if(args.size() == 0) {
      for (String commandName : ((EnvironmentImpl) env).getCommands().keySet()) {
        env.writeln(commandName);
      }
      return ShellStatus.CONTINUE;
    }

    if(((EnvironmentImpl) env).getCommands().keySet().contains(args.get(0))) {
      for (String description : ((EnvironmentImpl) env).getCommands().get(args.get(0)).getCommandDescription()) {
        env.writeln(description);
      }
    }

    return ShellStatus.CONTINUE;
  }

  @Override
  public String getCommandName() {
    return HELP_COMMAND;
  }

  @Override
  public List<String> getCommandDescription() {
    List<String> description = new ArrayList<>();
    description.add("Command " + HELP_COMMAND + " prints out all commands or deatiled description of one command.");
    description.add("Usage: " + HELP_COMMAND + " [COMMAND]");
    description.add("If called with one argument lists deatiled description about that command. "
        + "If called with no arguments, lists all available commands.");
    return description;
  }

}
