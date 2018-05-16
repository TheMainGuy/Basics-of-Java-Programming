package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Implements change directory shell command. Changes current working directory
 * to given path. If path does not exist, working directory will remain
 * unchanged. Path can be given in form of absolute path or relative path.
 * 
 * @author tin
 *
 */
public class CdShellCommand implements ShellCommand {

  /**
   * Defines cd string which user can use to change current working directory.
   */
  public static final String CD_COMMAND = "cd";

  @Override
  public ShellStatus executeCommand(Environment env, String arguments) {
    List<String> args = ArgumentParser.getArguments(arguments);
    if(args.size() != 1) {
      env.writeln(CD_COMMAND + " must be provided with one argument.");
      return ShellStatus.CONTINUE;
    }

    try {
      String path = env.getCurrentDirectory().resolve(Paths.get(args.get(0))).toString();
      env.setCurrentDirectory(Paths.get(path).normalize());
    } catch (IllegalArgumentException e) {
      env.writeln(e.getMessage());
    }

    return ShellStatus.CONTINUE;
  }

  @Override
  public String getCommandName() {
    return CD_COMMAND;
  }

  @Override
  public List<String> getCommandDescription() {
    List<String> description = new ArrayList<>();
    description.add("Command " + CD_COMMAND + " changes current working directory.");
    description.add("Usage: " + CD_COMMAND + " PATH");
    description.add("Must be called with one argument.");
    description.add("Changes current working directory to given path if that path exists.");
    description.add("If path does not exist, current working directory wiill remain unchanged.");
    return description;
  }

}
