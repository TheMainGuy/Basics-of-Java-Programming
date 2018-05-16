package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.commands.parser.NameBuilder;
import hr.fer.zemris.java.hw07.shell.commands.parser.NameBuilderInfo;
import hr.fer.zemris.java.hw07.shell.commands.parser.NameBuilderInfoImpl;
import hr.fer.zemris.java.hw07.shell.commands.parser.NameBuilderParser;

/**
 * Implements mass rename shell command. Command supports 4 different commands:
 * filter, groups, show and execute. Filter command prints out all files
 * matching given expression. Groups command prints out files matching given
 * expression and all groups defined in expression. Command show prints out
 * simulated renaming of all files matching first given expression. Command
 * execute moves/renames all files matching given expression.
 * 
 * @author tin
 *
 */
public class MassrenameShellCommand implements ShellCommand {
  /**
   * Defines mass rename string which user can use to move/rename all files
   * matching given expression.
   */
  public static final String MASS_RENAME_COMMAND = "massrename";

  /**
   * Defines filter command.
   */
  private static final String FILTER = "filter";

  /**
   * Defines groups command.
   */
  private static final String GROUPS = "groups";

  /**
   * Defines show command.
   */
  private static final String SHOW = "show";

  /**
   * Defines execute command
   */
  private static final String EXECUTE = "execute";

  @Override
  public ShellStatus executeCommand(Environment env, String arguments) {
    List<String> args = ArgumentParser.getArguments(arguments);
    if(args.size() < 4) {
      env.writeln(MASS_RENAME_COMMAND + " must be provided with four or five arguments.");
      return ShellStatus.CONTINUE;
    }
    if(!new File(env.getCurrentDirectory().resolve(args.get(0)).toString()).exists()
        || !new File(env.getCurrentDirectory().resolve(args.get(1)).toString()).exists()) {
      env.writeln("Directory " + args.get(0) + " does not exist.");
      return ShellStatus.CONTINUE;
    }

    try {
      List<String> output = null;
      if(args.get(2).equals(FILTER)) {
        output = getFilter(env, args);
        checkNumberOfArguments(args.size(), 4, FILTER);
      } else if(args.get(2).equals(GROUPS)) {
        checkNumberOfArguments(args.size(), 4, GROUPS);
        output = getGroups(env, args);
      } else if(args.get(2).equals(SHOW)) {
        checkNumberOfArguments(args.size(), 5, SHOW);
        output = getShow(env, args);
      } else if(args.get(2).equals(EXECUTE)) {
        checkNumberOfArguments(args.size(), 5, EXECUTE);
        output = execute(env, args);
      } else {
        env.writeln("Invalid CMD argument.");
      }

      for (String line : output) {
        env.writeln(line);
      }
    } catch (PatternSyntaxException e) {
      env.writeln(e.getMessage());
    } catch (IllegalArgumentException e) {
      env.writeln(e.getMessage());
    } catch (IOException e) {
      env.writeln(e.getMessage());
    }

    return ShellStatus.CONTINUE;
  }

  /**
   * Method simulates execute command in given environment and stores output in
   * {@link List} which it returns. Command execute moves/renames all files
   * matching given expression.
   * 
   * @param env environment in which command is executed
   * @param args command line arguments
   * @return {@link List} of output lines
   * @throws IOException
   */
  private List<String> execute(Environment env, List<String> args) throws IOException {
    List<String> output = getShow(env, args);
    for (int i = 0, size = output.size(); i < size; i++) {
      String[] parts = output.get(i).split(" => ");
      String source = env.getCurrentDirectory().resolve(args.get(0)).resolve(parts[0]).toString();
      String destination = env.getCurrentDirectory().resolve(args.get(1)).toString();
      destination = destination + "/" + parts[1];
      Files.move(Paths.get(source), Paths.get(destination));
      output.set(i, new StringBuilder().append(args.get(0)).append("/").append(parts[0]).append(" => ")
          .append(args.get(1)).append("/").append(parts[1]).toString());
    }
    return output;
  }

  /**
   * Method simulates show command in given environment and stores output in
   * {@link List} which it returns. Command show prints out simulated renaming of
   * all files matching first given expression.
   * 
   * @param env environment in which command is executed
   * @param args command line arguments
   * @return {@link List} of output lines
   */
  private List<String> getShow(Environment env, List<String> args) {
    List<String> output = getFilter(env, args);

    for (int i = 0, size = output.size(); i < size; i++) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(output.get(i)).append(" => ");

      NameBuilderParser parser = new NameBuilderParser(args.get(4));
      NameBuilder builder = parser.getNameBuilder();
      Pattern pattern = Pattern.compile(args.get(3));
      Matcher matcher = pattern.matcher(output.get(i));
      NameBuilderInfo info = new NameBuilderInfoImpl(matcher);
      builder.execute(info);
      String newName = info.getStringBuilder().toString();
      output.set(i, stringBuilder.append(newName).toString());
    }

    return output;
  }

  /**
   * Method simulates groups command in given environment and stores output in
   * {@link List} which it returns. Groups command prints out files matching given
   * expression and all groups defined in expression.
   * 
   * @param env environment in which command is executed
   * @param args command line arguments
   * @return {@link List} of output lines
   */
  private List<String> getGroups(Environment env, List<String> args) {
    List<String> output = getFilter(env, args);

    Pattern pattern = Pattern.compile(args.get(3));
    for (int i = 0, size = output.size(); i < size; i++) {
      Matcher matcher = pattern.matcher(output.get(i));
      matcher.matches();
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(output.get(i));
      for (int j = 0; j < matcher.groupCount() + 1; j++) {
        stringBuilder.append(" ").append(j).append(": ");
        stringBuilder.append(matcher.group(j));
      }
      output.set(i, stringBuilder.toString());
    }

    return output;

  }

  /**
   * Method simulates filter command in given environment and stores output in
   * {@link List} which it returns. Filter command prints out all files matching
   * given expression.
   * 
   * @param env environment in which command is executed
   * @param args command line arguments
   * @return {@link List} of output lines
   */
  private List<String> getFilter(Environment env, List<String> args) {
    List<String> output = new ArrayList<>();

    File directory = new File(env.getCurrentDirectory().resolve(Paths.get(args.get(0))).toString());
    File[] children = directory.listFiles();
    Pattern pattern = Pattern.compile(args.get(3));
    for (File file : children) {
      if(file.isDirectory()) {
        continue;
      }
      Matcher matcher = pattern.matcher(file.getName());
      if(matcher.matches()) {
        output.add(file.getName());
      }
    }

    return output;

  }

  /**
   * Helper method used for checking the number of arguments. Method is given
   * number of arguments, expected number of arguments and name of the command
   * which checks for number of arguments. Throws exception if number of arguments
   * is not the same as expected.
   * 
   * @param numberOfArguments number of arguments given
   * @param expectedNumberOfArguments number of arguments expected
   * @param commandName command name
   * 
   * @throws IllegalArgumentException if number of arguments is not the same as
   *           expected
   */
  private void checkNumberOfArguments(int numberOfArguments, int expectedNumberOfArguments, String commandName) {
    if(numberOfArguments != expectedNumberOfArguments) {
      throw new IllegalArgumentException(
          "Command " + commandName + " expects " + expectedNumberOfArguments + " arguments.");
    }
  }

  @Override
  public String getCommandName() {
    return MASS_RENAME_COMMAND;
  }

  @Override
  public List<String> getCommandDescription() {
    List<String> description = new ArrayList<>();
    description.add("Command " + MASS_RENAME_COMMAND
        + " moves/renames all files from given directory that match given expression. It supports four commands.");
    description
        .add("Usage: " + MASS_RENAME_COMMAND + " SOURCE_DIRECTORY DESTINATION_DIRECTORY CMD EXPRESSION [EXPRESSION2]");
    description.add("Must be called with four or five arguments.");
    description.add("CMD can be " + FILTER + ", " + GROUPS + ", " + SHOW + " or " + EXECUTE
        + ", each defining a different behaviour.");
    description.add("Commands " + FILTER + " and " + GROUPS + " take 4 arguments. Commands " + SHOW + " and " + EXECUTE
        + " take 5 arguments.");
    description.add("Command " + FILTER + " prints out all files from SOURCE_DIRECTORY" + " matching EXPRESSION.");
    description.add("Command " + GROUPS + " prints out all files from SOURCE_DIRECTORY matching EXPRESSION and "
        + "all groups defined in EXPRESSION");
    description.add("Command " + SHOW + " prints out all files from SOURCE_DIRECTORY matching EXPRESSION and "
        + "what their name and path would be after renaming to EXPRESSION2 and moving to DESTINATION_DIRECTORY.");
    description.add("Command " + EXECUTE + " executes massrename command. If SOURCE_DIRECTORY and "
        + "DESTINATION DIRECTORY are the same, renaming is performed. If they are not, moving is preformed."
        + " All files matching EXPRESSION from SOURCE_DIRECTORY are "
        + "renamed to EXPRESSION2 and moved to DESTINATION_DIRECTORY");
    return description;
  }
}
