package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Implements shell command used for changing shell symbols. Symbols that can be
 * changed: prompt symbol, more lines symbol and multi line symbol. Valid symbol
 * is any character. Symbol can not be an array of characters.
 * 
 * @author tin
 *
 */
public class SymbolShellCommand implements ShellCommand {

  /**
   * Defines symbol change string which user can use to change shell symbols.
   */
  public static final String SYMBOL_COMMAND = "symbol";

  /**
   * Defines prompt symbol argument. If this argument is used, prompt symbol will
   * be set to symbol given after this argument.
   */
  private static final String PROMPT_SYMBOL_KEYWORD = "PROMPTSYMBOL";

  /**
   * Defines more lines symbol argument. If this argument is used, more lines symbol will
   * be set to symbol given after this argument.
   */
  private static final String MORE_LINES_SYMBOL_KEYWORD = "MORELINESSYMBOL";

  /**
   * Defines multi line symbol argument. If this argument is used, multi line symbol will
   * be set to symbol given after this argument.
   */
  private static final String MULTI_LINE_SYMBOL_KEYWORD = "MULTILINESYMBOL";

  @Override
  public ShellStatus executeCommand(Environment env, String arguments) {
    List<String> args = ArgumentParser.getArguments(arguments);
    if(args.size() != 2) {
      env.writeln("Command symbol must be provided with 2 arguments.");
      return ShellStatus.CONTINUE;
    }
    if(args.get(1).length() != 1) {
      env.writeln("Invalid character.");
      return ShellStatus.CONTINUE;
    }
    if(args.get(0).equals(PROMPT_SYMBOL_KEYWORD)) {
      env.setPromptSymbol(args.get(1).charAt(0));
    } else if(args.get(0).equals(MORE_LINES_SYMBOL_KEYWORD)) {
      env.setMorelinesSymbol(args.get(1).charAt(0));
    } else if(args.get(0).equals(MULTI_LINE_SYMBOL_KEYWORD)) {
      env.setMultilineSymbol(args.get(1).charAt(0));
    } else {
      env.writeln("Invalid symbol argument.");
    }
    return ShellStatus.CONTINUE;
  }

  @Override
  public String getCommandName() {
    return SYMBOL_COMMAND;
  }

  @Override
  public List<String> getCommandDescription() {
    List<String> description = new ArrayList<>();
    description.add("Command " + SYMBOL_COMMAND + " changes one of shell symbols.");
    description.add("Usage: " + SYMBOL_COMMAND + " SYMBOL_TO_BE_CHANGED NEW_SYMBOL");
    description.add("Prompt symbol can be changed by giving " + PROMPT_SYMBOL_KEYWORD
        + " as first argument and desired symbol as second argument.");
    description.add("More lines symbol can be changed by giving " + MORE_LINES_SYMBOL_KEYWORD
        + " as first argument and desired symbol as second argument.");
    description.add("Multi line symbol can be changed by giving " + MULTI_LINE_SYMBOL_KEYWORD
        + " as first argument and desired symbol as second argument.");
    return description;
  }

}
