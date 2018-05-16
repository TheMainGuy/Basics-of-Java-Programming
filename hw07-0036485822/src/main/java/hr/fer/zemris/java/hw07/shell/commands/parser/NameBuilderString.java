package hr.fer.zemris.java.hw07.shell.commands.parser;

/**
 * Implements {@link NameBuilder} object which, after execute is called, appends
 * namePart to info's {@link StringBuilder} object.
 * 
 * @author tin
 *
 */
public class NameBuilderString implements NameBuilder {
  /**
   * Part of the name that will be appended to given info when execute is called.
   */
  private String namePart;

  /**
   * Constructor.
   * 
   * @param namePart name part
   */
  public NameBuilderString(String namePart) {
    this.namePart = namePart;
  }

  @Override
  public void execute(NameBuilderInfo info) {
    info.getStringBuilder().append(namePart);
  }
}
