package hr.fer.zemris.java.hw07.shell;

import java.nio.file.Path;
import java.util.Map;
import java.util.SortedMap;

/**
 * Defines method for shell environment. Has methods for communication with user
 * nad getting informationa about commands and environment symbols. Environment
 * should have list of commands and their description and default symbols.
 * 
 * @author tin
 *
 */
public interface Environment {
  /**
   * Method reads one line from user input. Input can be expanded using defined
   * more lines character in which case another line will be read from input.
   * Throws exception if there is problem with reading from implemented input.
   * 
   * @return read line
   * @throws ShellIOException if there is problem with reading from implemented
   *           input
   */
  String readLine() throws ShellIOException;

  /**
   * Method writes given text to output. Throws exception if there is problem with
   * writing to implemented output.
   * 
   * @param text text to be written
   * @throws ShellIOException if there is problem with writing to implemented
   *           output
   */
  void write(String text) throws ShellIOException;

  /**
   * Method writes given text to output with line separator after last character.
   * Throws exception if there is problem with writing to implemented output.
   * 
   * @param text text to be written
   * @throws ShellIOException if there is problem with writing to implemented
   *           output
   */
  void writeln(String text) throws ShellIOException;

  /**
   * Returns {@link SortedMap} of commands from an environment where name of
   * commands are keys and {@link ShellCommand} objects are values.
   * 
   * @return {@link SortedMap} of commands
   */
  SortedMap<String, ShellCommand> commands();

  /**
   * Returns current multi line symbol.
   * 
   * @return current multi line symbol
   */
  Character getMultilineSymbol();

  /**
   * Sets current multi line symbol to given symbol.
   * 
   * @param symbol new multi line symbol
   */
  void setMultilineSymbol(Character symbol);

  /**
   * Returns current prompt symbol.
   * 
   * @return current prompt symbol
   */
  Character getPromptSymbol();

  /**
   * Sets current prompt symbol to given symbol.
   * 
   * @param symbol new prompt symbol
   */
  void setPromptSymbol(Character symbol);

  /**
   * Returns current more lines symbol.
   * 
   * @return current more lines symbol
   */
  Character getMorelinesSymbol();

  /**
   * Sets current more lines symbol to given symbol.
   * 
   * @param symbol new more lines symbol
   */
  void setMorelinesSymbol(Character symbol);

  /**
   * Returns current directory path.
   * 
   * @return current directory path
   */
  Path getCurrentDirectory();

  /**
   * Sets current directory path.
   * 
   * @param path current directory path to be set
   */
  void setCurrentDirectory(Path path);

  /**
   * Returns data for given key.
   * 
   * @param key key
   * @return data data for given key
   */
  Object getSharedData(String key);

  /**
   * Adds key value pair to shared data map. Shared data is used for sharing data
   * between commands. Data is stored in {@link Map} using key value pairs.
   * 
   * @param key data key
   * @param value data value
   */
  void setSharedData(String key, Object value);
}
