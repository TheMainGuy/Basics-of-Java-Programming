package hr.fer.zemris.java.hw07.shell.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Implements cat command which takes 1 or 2 arguments and writes the content of
 * a file to user output. First argument is path to the file to be read and is
 * required. Second argument is optional and if provided, determines charset
 * used when reading from a file.
 * 
 * @author tin
 *
 */
public class CatShellCommand implements ShellCommand {
  /**
   * Defines cat string which user can use to print out file content.
   */
  public static final String CAT_COMMAND = "cat";

  @Override
  public ShellStatus executeCommand(Environment env, String arguments) {
    List<String> args = ArgumentParser.getArguments(arguments);
    Charset charset;
    if(args.size() < 1 || args.size() > 2) {
      env.writeln("Command " + CAT_COMMAND + " must have one or two arguments.");
      return ShellStatus.CONTINUE;
    }
    if(args.size() == 2) {
      charset = Charset.forName(args.get(1));
    } else {
      charset = Charset.defaultCharset();
    }
    try {
      BufferedReader fileReader = Files
          .newBufferedReader(env.getCurrentDirectory().resolve(Paths.get(args.get(0))), charset);
      String line;
      while ((line = fileReader.readLine()) != null) {
        env.writeln(line);
      }
    } catch (IOException e) {
      env.writeln(CAT_COMMAND + " had problem with reading file: " + args.get(0));
      return ShellStatus.CONTINUE;
    }

    return ShellStatus.CONTINUE;
  }

  @Override
  public String getCommandName() {
    return CAT_COMMAND;
  }

  @Override
  public List<String> getCommandDescription() {
    List<String> description = new ArrayList<>();
    description.add("Command " + CAT_COMMAND + " prints out content of given file.");
    description.add("Usage: " + CAT_COMMAND + " FILE_NAME [CHARSET]");
    description.add("If more than one argument is provided, file will be read using second argument as charset.");
    description.add("");
    return description;
  }

}
