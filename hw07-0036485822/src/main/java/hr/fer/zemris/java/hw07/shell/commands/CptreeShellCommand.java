package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Implements copy tree command which copies all directories and files
 * recursively from given directory to another given directory.
 * 
 * @author tin
 *
 */
public class CptreeShellCommand implements ShellCommand {
  /**
   * Defines copy tree string which user can use to copy all directories and files
   * recursively.
   */
  public static final String COPY_TREE_COMMAND = "cptree";

  @Override
  public ShellStatus executeCommand(Environment env, String arguments) {
    List<String> args = ArgumentParser.getArguments(arguments);
    if(args.size() != 2) {
      env.writeln(COPY_TREE_COMMAND + " must be provided with two arguments.");
      return ShellStatus.CONTINUE;
    }

    File source = new File(env.getCurrentDirectory().resolve(Paths.get(args.get(0))).toString());
    File destination = new File(env.getCurrentDirectory().resolve(Paths.get(args.get(1))).toString());
    if(!source.exists()) {
      env.writeln("Source directory does not exist.");
      return ShellStatus.CONTINUE;
    }
    if(!source.isDirectory()) {
      env.writeln("Source is not a directory.");
      return ShellStatus.CONTINUE;
    }
    if(destination.exists()) {
      destination = new File(
          env.getCurrentDirectory().resolve(Paths.get(args.get(1))).toString() + "/" + source.getName());
    } else if(!destination.exists() && destination.getParent() != null) {
      destination = new File(env.getCurrentDirectory().resolve(Paths.get(args.get(1))).toString());
    } else {
      env.writeln("Destination can not be determined.");
      return ShellStatus.CONTINUE;
    }

    destination.mkdir();
    copyTree(source, destination);
    return ShellStatus.CONTINUE;
  }

  /**
   * Helper method which copies all directories and files from given directory to
   * another given directory.
   * 
   * @param source source directory
   * @param destination destination directory
   */
  private void copyTree(File source, File destination) {
    File[] children = source.listFiles();
    for (File file : children) {
      if(file.isDirectory()) {
        File subDestination = new File(destination.toString() + "/" + file.getName());
        subDestination.mkdir();
        copyTree(file, subDestination);
      } else {
        try {
          InputStream reader = Files.newInputStream(Paths.get(file.toString()));
          OutputStream writer = Files.newOutputStream(Paths.get(destination.toString() + "/" + file.getName()));
          byte[] buffer = new byte[4096];
          int bytesRead;
          while ((bytesRead = reader.read(buffer)) > 0) {
            writer.write(buffer, 0, bytesRead);
          }
        } catch (IOException e) {
        }
      }
    }
  }

  @Override
  public String getCommandName() {
    return COPY_TREE_COMMAND;
  }

  @Override
  public List<String> getCommandDescription() {
    List<String> description = new ArrayList<>();
    description.add("Command " + COPY_TREE_COMMAND
        + " copies all directories and files recursively from given directory to another given directory.");
    description.add("Usage: " + COPY_TREE_COMMAND + " SOURCE_DIRECTORY DESTINATION_DIRECTORY");
    description.add("Must be called with two arguments.");
    description.add(
        "If DESTINATION_DIRECTORY does not exists, " + "directories and files will be copied to its parent directory.");
    description.add("If DESTINATION_DIRECTORY's parent directory does not exist, nothing will be copied.");
    return description;
  }
}
