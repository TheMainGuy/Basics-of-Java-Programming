package hr.fer.zemris.java.hw07.shell.commands.parser;

/**
 * Implements {@link NameBuilder} object which, after execute is called, appends
 * targeted group with specified padding to info's {@link StringBuilder} object.
 * 
 * @author tin
 *
 */
public class NameBuilderGroup implements NameBuilder{
  /**
   * Number of group which will be appended.
   */
  private int groupNumber;

  /**
   * Character which will be padded if minimalLength is greater than group's
   * length.
   */
  private char padChar;

  /**
   * Minimal number of characters which will be appended.
   */
  private int minimalLength;

  /**
   * Constructor.
   * 
   * @param groupNumber group number
   * @param padChar padding character
   * @param minimalLength minimal length
   */
  public NameBuilderGroup(int groupNumber, char padChar, int minimalLength) {
    this.groupNumber = groupNumber;
    this.padChar = padChar;
    this.minimalLength = minimalLength;
  }

  @Override
  public void execute(NameBuilderInfo info) {
    StringBuilder stringBuilder = info.getStringBuilder();
    for(int i = info.getGroup(groupNumber).length(); i < minimalLength; i++) {
      stringBuilder.append(padChar);
    }
    stringBuilder.append(info.getGroup(groupNumber));
    
  }

}
