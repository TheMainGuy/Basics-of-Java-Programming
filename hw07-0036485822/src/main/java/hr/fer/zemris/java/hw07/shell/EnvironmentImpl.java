package hr.fer.zemris.java.hw07.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

/**
 * Implements {@link Environment} methods to form a basic shell environment.
 * Initialized when starting shell to provide shell basic methods for calling
 * shell commands, managing environment characters and user input and output.
 * 
 * @author tin
 *
 */
public class EnvironmentImpl implements Environment {
  /**
   * Defines default PROMPT_SYMBOL character which will be printed out as first
   * character every time shell expects first line of input from user.
   */
  private static final char DEFAULT_PROMPT_SYMBOL = '>';

  /**
   * Defines default MORE_LINES_SYMBOL character which user uses to signal he will
   * give command in more than one line.
   */
  private static final char DEFAULT_MORE_LINES_SYMBOL = '\\';

  /**
   * Defines default MULTI_LINE_SYMBOL character which will be printed out as
   * first character instead of PROMPT_SYMBOl if MORE_LINES_SYMBOL is used at the
   * end of previous line.
   */
  private static final char DEFAULT_MULTI_LINE_SYMBOL = '|';
  
  /**
   * Defines map key for pushd and popd commands.
   */
  public static final String CD_STACK = "cdstack";

  /**
   * Promp symbol which will be printed out as first character every time shell
   * expects first line of input from user.
   */
  private char promptSymbol;

  /**
   * More lines symbol which user uses to signal he will give command in more than
   * one line.
   */
  private char moreLinesSymbol;

  /**
   * Multi lines symbol which will be printed out as first character instead of
   * promptSymbol if moreLinesSymbol is used at the end of previous line.
   */
  private char multiLineSymbol;

  /**
   * Map of all available commands.
   */
  private SortedMap<String, ShellCommand> commands;

  /**
   * User input.
   */
  private BufferedReader reader;

  /**
   * User output.
   */
  private BufferedWriter writer;

  /**
   * Path to current directory used for executing commands.
   */
  private Path currentDirectory;

  /**
   * Map of data used to share data among commands.
   */
  private Map<String, Object> sharedData = new HashMap<>();

  /**
   * Constructor. Defines user input and outpur, {@link SortedMap} of commands and
   * sets environment symbols to default values.
   * 
   * @param commands all commands currently available
   * @param reader user input
   * @param writer user ouput
   */
  public EnvironmentImpl(SortedMap<String, ShellCommand> commands, BufferedReader reader, BufferedWriter writer,
      Path currentDirectory) {
    promptSymbol = DEFAULT_PROMPT_SYMBOL;
    moreLinesSymbol = DEFAULT_MORE_LINES_SYMBOL;
    multiLineSymbol = DEFAULT_MULTI_LINE_SYMBOL;
    this.commands = Collections.unmodifiableSortedMap(commands);
    this.reader = reader;
    this.writer = writer;
    this.currentDirectory = currentDirectory;
  }

  @Override
  public String readLine() throws ShellIOException {
    String input;
    try {
      input = reader.readLine();
    } catch (IOException e) {
      throw new ShellIOException(e.getMessage());
    }

    if(input.endsWith(Character.toString(moreLinesSymbol))) {
      input = input.substring(0, input.length() - 1);
      write(multiLineSymbol + " ");
      input += readLine();
    }
    return input;
  }

  @Override
  public void write(String text) throws ShellIOException {
    try {
      writer.write(text);
      writer.flush();
    } catch (IOException e) {
      throw new ShellIOException(e.getMessage());
    }

  }

  @Override
  public void writeln(String text) throws ShellIOException {
    try {
      writer.write(text + "\n");
      writer.flush();
    } catch (IOException e) {
      throw new ShellIOException(e.getMessage());
    }
  }

  @Override
  public SortedMap<String, ShellCommand> commands() {
    return commands;
  }

  @Override
  public Character getMultilineSymbol() {
    return multiLineSymbol;
  }

  @Override
  public void setMultilineSymbol(Character symbol) {
    multiLineSymbol = symbol;

  }

  @Override
  public Character getPromptSymbol() {
    return promptSymbol;
  }

  @Override
  public void setPromptSymbol(Character symbol) {
    promptSymbol = symbol;
  }

  @Override
  public Character getMorelinesSymbol() {
    return moreLinesSymbol;
  }

  @Override
  public void setMorelinesSymbol(Character symbol) {
    moreLinesSymbol = symbol;
  }

  /**
   * Returns environment commands.
   * 
   * @return {@link SortedMap} of commands.
   */
  public SortedMap<String, ShellCommand> getCommands() {
    return commands;
  }

  @Override
  public Path getCurrentDirectory() {
    return currentDirectory;
  }

  @Override
  public void setCurrentDirectory(Path path) {
    if(Files.exists(path)) {
      currentDirectory = path;
      return;
    }
    throw new IllegalArgumentException("Path " + path + " does not exist.");
    
  }

  @Override
  public Object getSharedData(String key) {
    return sharedData.get(key);
  }

  @Override
  public void setSharedData(String key, Object value) {
    sharedData.put(key, value);
  }
}
