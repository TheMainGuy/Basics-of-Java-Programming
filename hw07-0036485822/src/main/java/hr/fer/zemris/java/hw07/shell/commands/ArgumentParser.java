package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class used to extract arguments for shell commands. Arguments are separated
 * by spaces. If under double quotation, spaces are treated as normal
 * characters.
 * 
 * @author tin
 *
 */
public class ArgumentParser {

  /**
   * Method splits arguments around spaces. If there are spaces between double
   * quotation, they are treated as normal characters.
   * 
   * @param args {@link String} of arguments
   * @return array of arguments
   */
  public static List<String> getArguments(String args) {
    
    Matcher matcher = Pattern.compile("[^ \"]+|\"([^\"]*[^\\\\])\"|\"(([^\"]|\\\")*)\"").matcher(args);
    List<String> arguments = new ArrayList<>();

    while (matcher.find()) {
      if(matcher.group(1) != null) {
        String s = matcher.group(1);
        s = s.replaceAll("\\\\\"", "\"");
        s.replaceAll("\\\\\\\\", "\\\\");
        arguments.add(s);
      } else if(matcher.group(2) != null) {
        String s = matcher.group(2);
        s = s.replaceAll("\\\\\"", "\"");
        s.replaceAll("\\\\\\\\", "\\\\");
        arguments.add(s);
      }
      else {
        arguments.add(matcher.group());
      }
    }
    return arguments;
  }
}
