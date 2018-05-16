package hr.fer.zemris.java.hw07.shell.commands.parser;

/**
 * Defines name builder information used to store names. Has
 * {@link StringBuilder} object on which {@link String}s can be appended to form
 * a name.
 * 
 * @author tin
 *
 */
public interface NameBuilderInfo {
  /**
   * Returns {@link StringBuilder} object.
   * 
   * @return string builder
   */
  StringBuilder getStringBuilder();

  /**
   * Returns matched group for specified index.
   * 
   * @param index index
   * @return group for specified index
   */
  String getGroup(int index);
}
