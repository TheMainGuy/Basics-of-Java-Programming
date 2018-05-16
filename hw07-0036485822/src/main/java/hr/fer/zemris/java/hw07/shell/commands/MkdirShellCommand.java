package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Implements mkdir shell command. Makes new directory.
 * 
 * @author tin
 *
 */
public class MkdirShellCommand implements ShellCommand{
  /**
   * Defines mkdir string which user can use to make new directory.
   */
  public static final String MKDIR_COMMAND = "mkdir";

  @Override
  public ShellStatus executeCommand(Environment env, String arguments) {
    List<String> args = ArgumentParser.getArguments(arguments);
    if(args.size() != 1) {
      env.writeln("Command " + MKDIR_COMMAND + " must have one argument.");
      return ShellStatus.CONTINUE;
    }
    new File(env.getCurrentDirectory().resolve(Paths.get(args.get(0))).toString()).mkdir();
    return ShellStatus.CONTINUE;
  }

  @Override
  public String getCommandName() {
    return MKDIR_COMMAND;
  }

  @Override
  public List<String> getCommandDescription() {
    List<String> description = new ArrayList<>();
    description.add("Command " + MKDIR_COMMAND + " creates new directory.");
    description.add("Usage: " + MKDIR_COMMAND + " DIRECTORY_NAME");
    description.add("If more than one argument is provided, directory will not be created.");
    return description;
  }
  
  
}
