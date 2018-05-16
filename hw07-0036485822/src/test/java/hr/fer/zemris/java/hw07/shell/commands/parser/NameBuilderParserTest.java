package hr.fer.zemris.java.hw07.shell.commands.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class NameBuilderParserTest {
  @Test
  public void regexTest() {
    Pattern pattern = Pattern.compile("\\$\\{[ ]*([\\d]*)[ ]*\\}");
    Matcher matcher = pattern.matcher("gradovi-${2}-${1,03}.jpg");
    matcher.matches();
    while (matcher.find()) {
      if(matcher.group(1) != null) {
        System.out.println(Integer.parseInt(matcher.group(1)));
      } else if(matcher.group(2) != null) {
        if(matcher.group(3) == null) {
          throw new IllegalArgumentException("Given expression is not valid.");
        }

        char padChar = ' ';
        int minimalLength = Integer.parseInt(matcher.group(3));
        if(matcher.group(3).charAt(0) == '0') {
          padChar = '0';
          minimalLength = Integer.parseInt(matcher.group(3).substring(1));
        }
        System.out.println(Integer.parseInt(matcher.group(2)) + padChar + minimalLength);
      } else if(matcher.group(4) != null) {
        System.out.println(matcher.group(4));
      } else {
        
      }
    }
  }
}
