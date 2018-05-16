package hr.fer.zemris.java.hw07.shell.commands;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.EnvironmentImpl;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Implements push directory shell command which pushes current directory
 * pointer to stack and changes directory to given directory. Allows user to
 * change diretories with saving reference to previous directory pointer.
 * 
 * @author tin
 *
 */
public class PushdShellCommand implements ShellCommand {

  /**
   * Defines push directory string which user can use to push current directory
   * pointer to stack and change directory to given directory.
   */
  public static final String PUSH_DIRECTORY_COMMAND = "pushd";

  @SuppressWarnings("unchecked")
  @Override
  public ShellStatus executeCommand(Environment env, String arguments) {
    List<String> args = ArgumentParser.getArguments(arguments);
    if(args.size() != 1) {
      env.writeln(PUSH_DIRECTORY_COMMAND + " must be provided with one argument.");
      return ShellStatus.CONTINUE;
    }
    try {
      Path oldPath = env.getCurrentDirectory();
      String path = env.getCurrentDirectory().resolve(Paths.get(args.get(0))).toString();
      env.setCurrentDirectory(Paths.get(new URI(path).normalize().getPath()));
      if(env.getSharedData(EnvironmentImpl.CD_STACK) == null) {
        env.setSharedData(EnvironmentImpl.CD_STACK, new Stack<Path>());
      }
      ((Stack<Path>) env.getSharedData(EnvironmentImpl.CD_STACK)).push(oldPath);
    } catch (IllegalArgumentException e) {
      env.writeln(e.getMessage());
    } catch (URISyntaxException e) {
      env.writeln(e.getMessage());
    }
    
    return ShellStatus.CONTINUE;
    
    
  }

  @Override
  public String getCommandName() {
    return PUSH_DIRECTORY_COMMAND;
  }

  @Override
  public List<String> getCommandDescription() {
    List<String> description = new ArrayList<>();
    description.add(
        "Command " + PUSH_DIRECTORY_COMMAND + 
        " changes directory and saves reference to current directory on stack.");
    description.add("Usage: " + PUSH_DIRECTORY_COMMAND + " PATH");
    description.add("Must be called with one argument.");
    return description;
  }

}
