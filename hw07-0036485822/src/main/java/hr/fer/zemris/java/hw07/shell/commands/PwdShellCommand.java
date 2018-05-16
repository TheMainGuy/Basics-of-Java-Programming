package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Implements print working directory shell command. Command prints current
 * working directory to user output.
 * 
 * @author tin
 *
 */
public class PwdShellCommand implements ShellCommand{

  /**
   * Defines pwd string which user can use to print working directory.
   */
  public static final String PWD_COMMAND = "pwd";
  
  @Override
  public ShellStatus executeCommand(Environment env, String arguments) {
    List<String> args = ArgumentParser.getArguments(arguments);
    if(args.size() > 0) {
      env.writeln(PWD_COMMAND + " takes no arguments.");
      return ShellStatus.CONTINUE;
    }
    System.out.println(env.getCurrentDirectory());
    return ShellStatus.CONTINUE;
  }

  @Override
  public String getCommandName() {
    return PWD_COMMAND;
  }

  @Override
  public List<String> getCommandDescription() {
    List<String> description = new ArrayList<>();
    description.add("Command " + PWD_COMMAND + " prints working directory.");
    description.add("Usage: " + PWD_COMMAND);
    description.add("Must be called with no arguments.");
    description.add("Prints absolute path to current working directory.");
    description.add("Example /home/tin");
    return description;
  }

}
