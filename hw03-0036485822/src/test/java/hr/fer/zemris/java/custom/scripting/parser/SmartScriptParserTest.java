package hr.fer.zemris.java.custom.scripting.parser;

import org.junit.Assert;
import org.junit.Test;


public class SmartScriptParserTest {
  private void createInstanceOfParser(String docBody, String expectedMessage) {
    try{
      new SmartScriptParser(docBody);
    } catch (Exception e) {
      Assert.assertEquals(expectedMessage, e.getMessage());
      throw e;
    }
  }
  @Test(expected = SmartScriptParserException.class)
  public void basicTest() {
    String docBody = "{$for f 5 6 @$} fjalskddfjldkj {$end$} jjfafkjldf";
    createInstanceOfParser(docBody, "Variable name must start with a letter.");
  }
  
  @Test(expected = SmartScriptParserException.class)
  public void forTagNotClosed() {
    String docBody = "{$for f 5 6$} fjalsk";
    
    try{
      new SmartScriptParser(docBody);
    } catch (Exception e) {
      Assert.assertEquals("Every FOR tag must be closed with end tag.", e.getMessage());
      throw e;
    }
  }
  
  @Test(expected = SmartScriptParserException.class)
  public void closeTagExpected() {
    String docBody = "{$fdk";
    createInstanceOfParser(docBody, "Close tag expected.");
  }
  
  @Test(expected = SmartScriptParserException.class)
  public void tagWithoutName() {
    String docBody = "{$55$}";
    createInstanceOfParser(docBody, "Tag must have a name.");
  }
  
  @Test(expected = SmartScriptParserException.class)
  public void tooFewArgumetsInForLoop() {
    String docBody = "{$for f$} f";
    createInstanceOfParser(docBody, "Too few arguments in for loop.");
  }
  
  @Test(expected = SmartScriptParserException.class)
  public void endTagNotEmpty() {
    String docBody = "{$for f 5 6$} fjalskddfjldkj {$end not empty$} jjfafkjldf";
    createInstanceOfParser(docBody, "End tag must be empty.");
  }
 
  
}
