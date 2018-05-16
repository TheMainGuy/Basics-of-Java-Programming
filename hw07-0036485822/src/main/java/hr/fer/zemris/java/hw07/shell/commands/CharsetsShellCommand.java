package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Implements charset command which takes no arguments and lists all available
 * charsets.
 * 
 * @author tin
 *
 */
public class CharsetsShellCommand implements ShellCommand {
  /**
   * Defines charsets string which user can use to get a list of all available
   * charsets.
   */
  public static final String CHARSETS_COMMAND = "charsets";

  @Override
  public ShellStatus executeCommand(Environment env, String arguments) {
    if(ArgumentParser.getArguments(arguments).size() > 0) {
      env.writeln("Command " + CHARSETS_COMMAND + " can not have any arguments.");
      return ShellStatus.CONTINUE;
    }
    
    SortedMap<String, Charset> charsets = Charset.availableCharsets();
    env.writeln("Available charsets:");
    for(String key : charsets.keySet()) {
      env.writeln(key + " " + charsets.get(key));
    }
    return ShellStatus.CONTINUE;
  }

  @Override
  public String getCommandName() {
    return CHARSETS_COMMAND;
  }

  @Override
  public List<String> getCommandDescription() {
    List<String> description = new ArrayList<>();
    description.add("Command " + CHARSETS_COMMAND + " lists all available charsets.");
    description.add("Usage: " + CHARSETS_COMMAND);
    description.add("If one or more arguments are provided, charsets will not be listed.");
    return description;
  }

  
}
