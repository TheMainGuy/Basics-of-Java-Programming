package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.Assert.assertNotNull;

import org.junit.Assert;
import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.lexer.Lexer;

/**
 * Class used for testing {@link Lexer}
 * 
 * @author tin
 *
 */
public class LexerTest {
  @Test
  public void testNotNull() {
    Lexer lexer = new Lexer("");
    
    assertNotNull("Token was expected but null was returned.", lexer.nextToken());
  }
  
  @Test(expected=NullPointerException.class)
  public void testNullInput() {
    // must throw!
    new Lexer(null);
  }
  
  @Test
  public void testWordWithManyEscapes() {
    // Lets check for several words...
    Lexer lexer = new Lexer("  ab\\\\2cd3 ab\\\\cd4\\\\ \r\n\t   ");


    Assert.assertEquals("  ab\\2cd3 ab\\cd4\\ \r\n\t   ", lexer.nextToken().getValue().toString());
  }
  
  @Test
  public void test() {
    //serious testing
    Lexer lexer = new Lexer("fsdffaf{$for f 5 6 $} fjalskddfjldkj {$end$} jjfafkjldf");
    String[] data = {"fsdffaf", "{$", "for", "f", "5", "6", "$}", " fjalskddfjldkj ", "{$", "end", "$}", " jjfafkjldf"};
    for (int i = 0; i < 12; i++) {
      Assert.assertEquals(data[i], lexer.nextToken().toString());
      //System.out.println("token broj " + i + ": " + lexer.nextToken().toString());
      if(lexer.getToken().getType() == TokenType.TAG_OPEN) {
        lexer.setState(LexerState.TAG);
      }
      else if(lexer.getToken().getType() == TokenType.TAG_CLOSE) {
        lexer.setState(LexerState.TEXT);
      }
    }
    Assert.assertEquals(lexer.nextToken().getType(), TokenType.EOF);
  }
}
