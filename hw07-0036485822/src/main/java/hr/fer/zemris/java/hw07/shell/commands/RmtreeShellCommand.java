package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Implements remove tree command which removes all directories and files
 * recursively from given directory.
 * 
 * @author tin
 *
 */
public class RmtreeShellCommand implements ShellCommand {

  /**
   * Defines remove tree string which user can use to remove all directories and
   * files recursively.
   */
  public static final String REMOVE_TREE_COMMAND = "rmtree";

  @Override
  public ShellStatus executeCommand(Environment env, String arguments) {
    List<String> args = ArgumentParser.getArguments(arguments);
    if(args.size() != 1) {
      env.writeln(REMOVE_TREE_COMMAND + " must be provided with one argument.");
      return ShellStatus.CONTINUE;
    }

    try {
      removeTree(new File(env.getCurrentDirectory().resolve(Paths.get(args.get(0))).toString()));
    } catch (Exception e) {
      env.writeln("Given directory does not exist.");
    }
    return ShellStatus.CONTINUE;
  }

  /**
   * Helper method which removes all directories and files from given
   * directory.
   * 
   * @param directory directory
   */
  private void removeTree(File directory) {
    File[] children = directory.listFiles();
    for (File file : children) {
      if(file.isDirectory()) {
        removeTree(file);
      }
      file.delete();
    }
  }

  @Override
  public String getCommandName() {
    return REMOVE_TREE_COMMAND;
  }

  @Override
  public List<String> getCommandDescription() {
    List<String> description = new ArrayList<>();
    description
        .add("Command " + REMOVE_TREE_COMMAND + " removes all directories and files recursively from given directory.");
    description.add("Usage: " + REMOVE_TREE_COMMAND + " DIRECTORY");
    description.add("Must be called with one argument.");
    return description;
  }

}
