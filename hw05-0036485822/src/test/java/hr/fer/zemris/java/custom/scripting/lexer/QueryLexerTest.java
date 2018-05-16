package hr.fer.zemris.java.custom.scripting.lexer;

import org.junit.Assert;
import org.junit.Test;

public class QueryLexerTest {
  @Test
  public void simpleLexerTest() {
    QueryLexer lexer = new QueryLexer("jmbag = \"42094023\"");
    Assert.assertEquals("jmbag", lexer.nextToken().getValue().toString());
    Assert.assertEquals("=", lexer.nextToken().getValue().toString());
    Assert.assertEquals("\"42094023\"", lexer.nextToken().getValue().toString());
    Assert.assertTrue(lexer.nextToken().getType() == TokenType.EOF);
  }
  
  @Test(expected=LexerException.class)
  public void noTokensLeft() {
    QueryLexer lexer = new QueryLexer("jmbag = \"42094023\"");
    lexer.nextToken();
    lexer.nextToken();
    lexer.nextToken();
    lexer.nextToken();
    lexer.nextToken();
  }
  
  @Test
  public void expressionExample() {
    QueryLexer lexer = new QueryLexer("jmbag = \"0000000003\" AND lastName LIKE \"B*\"");
    Assert.assertEquals("jmbag", lexer.nextToken().getValue().toString());
    Assert.assertEquals("=", lexer.nextToken().getValue().toString());
    Assert.assertEquals("\"0000000003\"", lexer.nextToken().getValue().toString());
    Assert.assertEquals("AND", lexer.nextToken().getValue().toString());
    Assert.assertEquals("lastName", lexer.nextToken().getValue().toString());
    Assert.assertEquals("LIKE", lexer.nextToken().getValue().toString());
    Assert.assertEquals("\"B*\"", lexer.nextToken().getValue().toString());
  }
}
