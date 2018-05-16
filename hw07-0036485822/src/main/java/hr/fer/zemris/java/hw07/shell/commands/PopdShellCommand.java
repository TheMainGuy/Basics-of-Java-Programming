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
 * Implements pop directory shell command which pops directory pointer from
 * stack and changes directory to popped directory. Allows user to change
 * diretories from saved references to previous directory pointers.
 * 
 * @author tin
 *
 */
public class PopdShellCommand implements ShellCommand {
  /**
   * Defines pop directory string which user can use to pop current directory
   * pointer from stack and change directory to popped directory.
   */
  public static final String POP_DIRECTORY_COMMAND = "popd";

  @SuppressWarnings("unchecked")
  @Override
  public ShellStatus executeCommand(Environment env, String arguments) {
    List<String> args = ArgumentParser.getArguments(arguments);

    if(args.size() != 0) {
      env.writeln(POP_DIRECTORY_COMMAND + " must be called with no arguments.");
      return ShellStatus.CONTINUE;
    }
    if(env.getSharedData(EnvironmentImpl.CD_STACK) != null
        && !((Stack<Path>) env.getSharedData(EnvironmentImpl.CD_STACK)).isEmpty()) {
      new CdShellCommand().executeCommand(env,
          ((Stack<Path>) env.getSharedData(EnvironmentImpl.CD_STACK)).pop().toString());
    } else {
      env.writeln("No stored directories.");
    }

    return ShellStatus.CONTINUE;
  }

  @Override
  public String getCommandName() {
    return POP_DIRECTORY_COMMAND;
  }

  @Override
  public List<String> getCommandDescription() {
    List<String> description = new ArrayList<>();
    description.add("Command " + POP_DIRECTORY_COMMAND
        + " pops directory from stack made by pushd and changes directory to popped path.");
    description.add("Usage: " + POP_DIRECTORY_COMMAND);
    description.add("Must be called with no arguments.");
    return description;
  }

}
