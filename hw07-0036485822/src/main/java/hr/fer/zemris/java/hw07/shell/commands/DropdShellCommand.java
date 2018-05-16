package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.EnvironmentImpl;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Implements drop directory shell command which pops directory pointer from
 * stack. Used for removing reference to last directory pointer pushed on stack
 * with pushd command.
 * 
 * @author tin
 *
 */
public class DropdShellCommand implements ShellCommand{
  /**
   * Defines drop directory string which user can use to pop current directory
   * pointer from stack.
   */
  public static final String DROP_DIRECTORY_COMMAND = "dropd";

  @SuppressWarnings("unchecked")
  @Override
  public ShellStatus executeCommand(Environment env, String arguments) {
    List<String> args = ArgumentParser.getArguments(arguments);

    if(args.size() != 0) {
      env.writeln(DROP_DIRECTORY_COMMAND + " must be called with no arguments.");
      return ShellStatus.CONTINUE;
    }
    
    if(env.getSharedData(EnvironmentImpl.CD_STACK) == null
        || ((Stack<Path>) env.getSharedData(EnvironmentImpl.CD_STACK)).isEmpty()) {
      env.writeln("No stored directories.");
    } else {
      ((Stack<Path>) env.getSharedData(EnvironmentImpl.CD_STACK)).pop();
    }
    
    return ShellStatus.CONTINUE;
  }

  @Override
  public String getCommandName() {
    return DROP_DIRECTORY_COMMAND;
  }

  @Override
  public List<String> getCommandDescription() {
    List<String> description = new ArrayList<>();
    description
        .add("Command " + DROP_DIRECTORY_COMMAND + " pops directory from stack made by pushd.");
    description.add("Usage: " + DROP_DIRECTORY_COMMAND);
    description.add("Must be called with no arguments.");
    return description;
  }

}
