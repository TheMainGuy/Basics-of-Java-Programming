package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Implements tree command listing all directories and files recursively from
 * given directory.
 * 
 * @author tin
 *
 */
public class TreeShellCommand implements ShellCommand {
  /**
   * Defines tree string which user can use to print out all directories and files
   * recursively.
   */
  public static final String TREE_COMMAND = "tree";

  /**
   * String which will be used when shifting directory levels in tree.
   */
  private final String LEVEL_SHIFT = "  ";

  @Override
  public ShellStatus executeCommand(Environment env, String arguments) {
    List<String> args = ArgumentParser.getArguments(arguments);
    if(args.size() != 1) {
      env.writeln(TREE_COMMAND + " must be provided with one argument.");
      return ShellStatus.CONTINUE;
    }

    printTreeInEnvironment(new File(env.getCurrentDirectory().resolve(Paths.get(args.get(0))).toString()), 0, env);
    return ShellStatus.CONTINUE;
  }

  /**
   * Helper method which prints out file tree in specified environment starting
   * from given directory.
   * 
   * @param directory directory from which tree starts
   * @param level current depth of tree
   * @param env environment
   */
  private void printTreeInEnvironment(File directory, int level, Environment env) {
    File[] children = directory.listFiles();
    for (File file : children) {
      for (int i = 0; i < level; i++) {
        env.write(LEVEL_SHIFT);
      }
      env.writeln(file.getName());
      if(file.isDirectory()) {
        printTreeInEnvironment(file, level + 1, env);
      }
    }
  }

  @Override
  public String getCommandName() {
    return TREE_COMMAND;
  }

  @Override
  public List<String> getCommandDescription() {
    List<String> description = new ArrayList<>();
    description.add("Command " + TREE_COMMAND + " prints out directories and files recursively from given directory.");
    description.add("Usage: " + TREE_COMMAND + " DIRECTORY");
    description.add("Must be called with one argument.");
    return description;
  }
}
