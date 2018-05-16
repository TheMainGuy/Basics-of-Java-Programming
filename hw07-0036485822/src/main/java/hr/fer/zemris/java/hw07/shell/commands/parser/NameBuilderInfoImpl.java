package hr.fer.zemris.java.hw07.shell.commands.parser;

import java.util.regex.Matcher;

/**
 * Implements {@link NameBuilderInfo} methods to handle {@link Matcher} objects
 * and allow returning mathed groups. Also stores {@link StringBuilder} object
 * which can be altered by calling getStringBuilder method and appending
 * {@link String}s.
 * 
 * @author tin
 *
 */
public class NameBuilderInfoImpl implements NameBuilderInfo {

  /**
   * String builder object which can be altered from outside of this class.
   */
  private StringBuilder stringBuilder;

  /**
   * Matcher used to store groups and allow getGroup method to work.
   */
  private Matcher matcher;

  /**
   * Constructor.
   * 
   * @param matcher matcher
   */
  public NameBuilderInfoImpl(Matcher matcher) {
    stringBuilder = new StringBuilder();
    this.matcher = matcher;
    this.matcher.matches();
  }

  @Override
  public StringBuilder getStringBuilder() {
    return stringBuilder;
  }

  @Override
  public String getGroup(int index) {
    return matcher.group(index);
  }

}
