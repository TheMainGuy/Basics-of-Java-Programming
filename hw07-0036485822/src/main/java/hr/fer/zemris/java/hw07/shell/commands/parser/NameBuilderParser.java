package hr.fer.zemris.java.hw07.shell.commands.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implements name builder parser which parses expression given in constructor.
 * Expression will be parsed into multiple {@link NameBuilder} objects with last
 * {@link NameBuilder} object storing all the others. Expression supports regex
 * syntax with extra grouping syntax.
 * 
 * @author tin
 *
 */
public class NameBuilderParser {

  /**
   * Name builder which contains all other {@link NameBuilder} objects and can
   * call execute on each one of them.
   */
  private NameBuilderList nameBuilderList;

  public NameBuilderParser(String expression) {
    nameBuilderList = new NameBuilderList();
    parse(expression);
  }

  private void parse(String expression) {
    Pattern pattern = Pattern
        .compile("\\$\\{[ ]*([\\d]*)[ ]*\\}|\\$\\{[ ]*([\\d])*[ ]*,[ ]*([0]?[1-9][\\d]*)[ ]*\\}|([^$]*)");
    Matcher matcher = pattern.matcher(expression);
    matcher.matches();
    while (matcher.find()) {
      if(matcher.group(1) != null) {
        nameBuilderList.add(new NameBuilderGroup(Integer.parseInt(matcher.group(1)), ' ', 0));
      }
      else if(matcher.group(2) != null) {
        if(matcher.group(3) == null) {
          throw new IllegalArgumentException("Given expression is not valid.");
        }
        
        char padChar = ' ';
        int minimalLength = Integer.parseInt(matcher.group(3));
        if(matcher.group(3).charAt(0) == '0') {
          padChar = '0';
          minimalLength = Integer.parseInt(matcher.group(3).substring(1));
        }
        nameBuilderList.add(new NameBuilderGroup(Integer.parseInt(matcher.group(2)), padChar, minimalLength));
      }
      else if(matcher.groupCount() > 3 && matcher.group(4) != null) {
        nameBuilderList.add(new NameBuilderString(matcher.group(4)));
      } else {
        nameBuilderList.add(new NameBuilderString(matcher.group()));
      }
    }
  }

  public NameBuilder getNameBuilder() {
    return nameBuilderList;
  }
}
