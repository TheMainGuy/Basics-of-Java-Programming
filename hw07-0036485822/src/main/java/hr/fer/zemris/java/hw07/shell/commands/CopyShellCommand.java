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
 * Implements file copy command
 * 
 * @author tin
 *
 */
public class CopyShellCommand implements ShellCommand {

  /**
   * Defines copy string which user can use to copy a file.
   */
  public static final String COPY_COMMAND = "copy";

  @Override
  public ShellStatus executeCommand(Environment env, String arguments) {
    List<String> args = ArgumentParser.getArguments(arguments);
    if(args.size() != 2) {
      env.writeln("Copy must be provided with two arguments.");
      return ShellStatus.CONTINUE;
    }
    File source = new File(env.getCurrentDirectory().resolve(Paths.get(args.get(0))).toString());
    File destination = new File(env.getCurrentDirectory().resolve(Paths.get(args.get(1))).toString());

    if(source.isDirectory()) {
      env.writeln("Source file can not be a directory.");
      return ShellStatus.CONTINUE;
    }

    if(destination.isDirectory()) {
      destination = new File(
          env.getCurrentDirectory().resolve(Paths.get(args.get(1))).toString() + "/" + source.getName());
    }

    if(destination.exists()) {
      env.writeln(destination.toString() + " already exists, are you sure you want to overwrite it? y/N");
      if(env.readLine().equals("y")) {
        env.writeln("File will be overwritten.");
      } else {
        env.writeln("Copy command canceled.");
        return ShellStatus.CONTINUE;
      }
    }

    try {
      InputStream reader = Files.newInputStream(Paths.get(source.toString()));
      OutputStream writer = Files.newOutputStream(Paths.get(destination.toString()));
      byte[] buffer = new byte[4096];
      int bytesRead;
      while ((bytesRead = reader.read(buffer)) > 0) {
        writer.write(buffer, 0, bytesRead);
      }
    } catch (IOException e) {
      env.writeln("Problem with reading or writing files.");
      return ShellStatus.CONTINUE;
    }

    env.writeln("File copied.");
    return ShellStatus.CONTINUE;
  }

  @Override
  public String getCommandName() {
    return COPY_COMMAND;
  }

  @Override
  public List<String> getCommandDescription() {
    List<String> description = new ArrayList<>();
    description.add("Command " + COPY_COMMAND + " copies a file.");
    description.add("Usage: " + COPY_COMMAND + " SOURCE_FILE DESTINATION_FILE");
    description.add("Must be called with two arguments and can not copy a directory.");
    description.add("If second argument is directory, copies file inside that directory using the original file name.");
    return description;
  }

}
